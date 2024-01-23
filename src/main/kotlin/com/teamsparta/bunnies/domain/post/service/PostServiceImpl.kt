package com.teamsparta.bunnies.domain.post.service

import com.teamsparta.bunnies.domain.exception.InvalidCredentialException
import com.teamsparta.bunnies.domain.exception.ModelNotFoundException
import com.teamsparta.bunnies.domain.post.dto.request.CreatePostDto
import com.teamsparta.bunnies.domain.post.dto.request.UpdatePostDto
import com.teamsparta.bunnies.domain.post.dto.response.PostDetailResponseDto
import com.teamsparta.bunnies.domain.post.dto.response.PostResponseDto
import com.teamsparta.bunnies.domain.post.model.PostEntity
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
    private val userRepository: UserRepository
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
        val entity = postRepository.save(PostEntity.toEntity(createPostDto, user))

        return PostResponseDto.toResponse(entity)
    }

    @Transactional
    override fun updatePost(
        postId: Long,
        updatePostDto: UpdatePostDto,
        userPrincipal: UserPrincipal
    ): PostResponseDto {
        val user = userRepository.findByIdOrNull(userPrincipal.id)
            ?: throw ModelNotFoundException("User", userPrincipal.id)

        val entity = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("Post", postId)

        // role이 ADMIN이거나 본인인 경우에만 수정 가능하도록 확인
        if (user.role != entity.author.role && user.id != entity.author.id) throw InvalidCredentialException("권한이 없습니다.")

        return entity
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

        val entity = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("Post", postId)

        // role이 ADMIN이거나 본인인 경우에만 수정 가능하도록 확인
        if (user.role != entity.author.role && user.id != entity.author.id) throw InvalidCredentialException("권한이 없습니다.")

        return entity
            .apply { isComplete() }
            .let { PostResponseDto.toResponse(it) }
    }

    @Transactional
    override fun deletePost(
        postId: Long,
        userPrincipal: UserPrincipal
    ) {
        val entity = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("Post", postId)

        val user = userRepository.findByIdOrNull(userPrincipal.id)
            ?: throw ModelNotFoundException("User", userPrincipal.id)

        // role이 ADMIN이거나 본인인 경우에만 삭제 가능하도록 확인
        if (user.role != entity.author.role && user.id != entity.author.id) throw InvalidCredentialException("권한이 없습니다.")

        postRepository.delete(entity)}
    }