package com.teamsparta.bunnies.domain.post.service

import com.teamsparta.bunnies.domain.post.dto.request.CreatePostDto
import com.teamsparta.bunnies.domain.post.dto.request.UpdatePostDto
import com.teamsparta.bunnies.domain.post.dto.response.PostDetailResponseDto
import com.teamsparta.bunnies.domain.post.dto.response.PostResponseDto
import com.teamsparta.bunnies.infra.security.UserPrincipal

interface PostService {
    fun getAllPost(page: Int, size: Int): List<PostResponseDto>

    fun getPost(postId: Long): PostDetailResponseDto

    fun createPost(createPostDto: CreatePostDto, userPrincipal: UserPrincipal): PostResponseDto

    fun updatePost(postId: Long, updatePostDto: UpdatePostDto, userPrincipal: UserPrincipal): PostResponseDto

    fun updateStatus(postId: Long, userPrincipal: UserPrincipal): PostResponseDto

    fun deletePost(postId: Long, userPrincipal: UserPrincipal)

    fun addLikes(postId: Long, userPrincipal: UserPrincipal)

    fun getLikes(postId: Long, userPrincipal: UserPrincipal)

    fun deleteLikes(postId: Long, userPrincipal: UserPrincipal)

}