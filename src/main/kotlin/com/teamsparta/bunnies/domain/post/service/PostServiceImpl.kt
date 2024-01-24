package com.teamsparta.bunnies.domain.post.service

import com.teamsparta.bunnies.domain.exception.InvalidCredentialException
import com.teamsparta.bunnies.domain.exception.ModelNotFoundException
import com.teamsparta.bunnies.domain.exception.UnauthorizedOperationException
import com.teamsparta.bunnies.domain.post.dto.request.CreatePostDto
import com.teamsparta.bunnies.domain.post.dto.request.UpdatePostDto
import com.teamsparta.bunnies.domain.post.dto.response.PostDetailResponseDto
import com.teamsparta.bunnies.domain.post.dto.response.PostResponseDto
import com.teamsparta.bunnies.domain.post.model.LikeEntity
import com.teamsparta.bunnies.domain.post.model.PostEntity
import com.teamsparta.bunnies.domain.post.repository.LikeRepository
import com.teamsparta.bunnies.domain.post.repository.PostRepository
import com.teamsparta.bunnies.domain.user.repository.UserRepository
import com.teamsparta.bunnies.infra.security.UserPrincipal
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val likeRepository: LikeRepository
): PostService {

    @Transactional(readOnly = true)
    override fun getAllPost(page: Int, size: Int): List<PostResponseDto> {

        return postRepository
            .findAllBy(PageRequest.of(page, size, Sort.by("createdAt").descending()))
            .map { PostResponseDto.toResponse(it) }
    }

    @Transactional(readOnly = true)
    override fun getPost(
        postId: Long
    ): PostDetailResponseDto {
        return postRepository
            .findByIdOrNull(postId)
            ?.let { PostDetailResponseDto.toResponse(it) }
            ?: throw ModelNotFoundException("Post", postId)
    }

    @Transactional
    override fun createPost(
        createPostDto: CreatePostDto,
        userPrincipal: UserPrincipal
    ): PostResponseDto {
        val user = userRepository.findByIdOrNull(userPrincipal.id)
            ?: throw ModelNotFoundException("User", userPrincipal.id)
        val post = postRepository.save(PostEntity.toEntity(createPostDto, user))

        return PostResponseDto.toResponse(post)
    }

    @Transactional
    override fun updatePost(
        postId: Long,
        updatePostDto: UpdatePostDto,
        userPrincipal: UserPrincipal
    ): PostResponseDto {
        val user = userRepository.findByIdOrNull(userPrincipal.id)
            ?: throw ModelNotFoundException("User", userPrincipal.id)

        val post = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("Post", postId)

        // role이 ADMIN이거나 본인인 경우에만 수정 가능하도록 확인
        if (user.role != post.author.role && user.id != post.author.id)
            throw InvalidCredentialException("권한이 없습니다.")

        return post
            .apply { update(updatePostDto) }
            .let { PostResponseDto.toResponse(it) }
    }

    @Transactional
    override fun updateStatus(
        postId: Long,
        userPrincipal: UserPrincipal
    ): PostResponseDto {
        val user = userRepository.findByIdOrNull(userPrincipal.id)
            ?: throw ModelNotFoundException("User", userPrincipal.id)

        val post = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("Post", postId)

        // role이 ADMIN이거나 본인인 경우에만 수정 가능하도록 확인
        if (user.role != post.author.role && user.id != post.author.id)
            throw InvalidCredentialException("권한이 없습니다.")

        return post
            .apply { isComplete() }
            .let { PostResponseDto.toResponse(it) }
    }

    @Transactional
    override fun deletePost(
        postId: Long,
        userPrincipal: UserPrincipal
    ) {
        val post = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("Post", postId)

        val user = userRepository.findByIdOrNull(userPrincipal.id)
            ?: throw ModelNotFoundException("User", userPrincipal.id)

        // role이 ADMIN이거나 본인인 경우에만 삭제 가능하도록 확인
        if (user.role != post.author.role && user.id != post.author.id)
            throw InvalidCredentialException("권한이 없습니다.")

        postRepository.delete(post)
    }

    override fun addLikes(
        postId: Long,
        userPrincipal: UserPrincipal
    ) {
        val post = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("Post", postId)

        if (post.author.id == userPrincipal.id)
            throw UnauthorizedOperationException("자신이 작성한 게시글에는 좋아요를 누를 수 없습니다.")

        if(likeRepository.existsByPostIdAndUserId(postId, userPrincipal.id)){
            throw IllegalStateException("이미 좋아요를 누르셨습니다.")
        }

        val user = userRepository.findByIdOrNull(userPrincipal.id)
            ?: throw ModelNotFoundException("User", userPrincipal.id)

        likeRepository.save(LikeEntity.toEntity(post, user))
    }

    override fun getLikes(
        postId: Long,
        userPrincipal: UserPrincipal
    ) {
        TODO("Not yet implemented")
    }

    override fun deleteLikes(postId: Long, userPrincipal: UserPrincipal) {
        val post = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("Post", postId)

        if (post.author.id == userPrincipal.id)
            throw UnauthorizedOperationException("자신이 작성한 게시글에는 좋아요를 누를 수 없습니다.")

        if(likeRepository.existsByPostIdAndUserId(postId, userPrincipal.id)){
            likeRepository.deleteByPostIdAndUserId(postId, userPrincipal.id)
        }
    }
}
