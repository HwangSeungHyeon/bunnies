package com.teamsparta.bunnies.domain.post.service

import com.teamsparta.bunnies.domain.post.dto.request.CreatePostDto
import com.teamsparta.bunnies.domain.post.dto.request.UpdatePostDto
import com.teamsparta.bunnies.domain.post.dto.response.PostDetailResponseDto
import com.teamsparta.bunnies.domain.post.dto.response.PostResponseDto

interface PostService {
    fun getAllPost(page: Int, size: Int): List<PostResponseDto>

    fun getPost(postId: Long): PostDetailResponseDto

    fun createPost(createPostDto: CreatePostDto): PostResponseDto

    fun updatePost(postId: Long, updatePostDto: UpdatePostDto): PostResponseDto

    fun updateStatus(postId: Long): PostResponseDto

    fun deletePost(postId: Long)
}