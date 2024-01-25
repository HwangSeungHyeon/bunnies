package com.teamsparta.bunnies.domain.comment.service

import com.teamsparta.bunnies.domain.comment.dto.CommentResponseDto
import com.teamsparta.bunnies.domain.comment.dto.CreateCommentRequestDto
import com.teamsparta.bunnies.domain.comment.dto.UpdateCommentRequestDto
import com.teamsparta.bunnies.infra.security.UserPrincipal

interface CommentService {
    fun createComment(request: CreateCommentRequestDto, postId: Long, userPrincipal: UserPrincipal): CommentResponseDto
    fun updateComment(postId: Long, commentId: Long, request: UpdateCommentRequestDto, userPrincipal: UserPrincipal): CommentResponseDto
    fun deleteComment(commentId:Long)
}