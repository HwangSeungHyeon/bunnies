package com.teamsparta.bunnies.domain.post.service

import com.teamsparta.bunnies.domain.exception.ModelNotFoundException
import com.teamsparta.bunnies.domain.post.dto.request.CreatePostDto
import com.teamsparta.bunnies.domain.post.dto.request.UpdatePostDto
import com.teamsparta.bunnies.domain.post.dto.response.PostDetailResponseDto
import com.teamsparta.bunnies.domain.post.dto.response.PostResponseDto
import com.teamsparta.bunnies.domain.post.model.PostEntity
import com.teamsparta.bunnies.domain.post.repository.PostRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostServiceImpl(
    private val postRepository: PostRepository
): PostService {
    override fun getAllPost(page: Int, size: Int): List<PostResponseDto> {

        return postRepository
            .findAllBy(PageRequest.of(page, size, Sort.by("createdAt").descending()))
            .map { PostResponseDto.toResponse(it) }
    }

    override fun getPost(
        postId: Long
    ): PostDetailResponseDto {
        return postRepository
            .findByIdOrNull(postId)
            ?.let { PostDetailResponseDto.toResponse(it) }
            ?: throw ModelNotFoundException("Post", postId)
    }

    override fun createPost(
        createPostDto: CreatePostDto
    ): PostResponseDto {
        val entity = postRepository.save(PostEntity.toEntity(createPostDto))
        return PostResponseDto.toResponse(entity)
    }

    @Transactional
    override fun updatePost(
        postId: Long,
        updatePostDto: UpdatePostDto
    ): PostResponseDto {
        val entity = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("Post", postId)

        return entity
            .apply { update(updatePostDto) }
            .let { PostResponseDto.toResponse(it) }
    }

    override fun updateStatus(
        postId: Long
    ): PostResponseDto {
        val entity = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("Post", postId)

        return entity
            .apply { isComplete() }
            .let { PostResponseDto.toResponse(it) }
    }

    @Transactional
    override fun deletePost(
        postId: Long
    ) {
        postRepository.findByIdOrNull(postId)
            ?.let{ postRepository.delete(it) }
            ?: throw ModelNotFoundException("Post", postId)
    }
}