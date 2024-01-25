package com.teamsparta.bunnies.domain.comment.service

import com.teamsparta.bunnies.domain.comment.dto.CommentResponseDto
import com.teamsparta.bunnies.domain.comment.dto.CreateCommentRequestDto
import com.teamsparta.bunnies.domain.comment.dto.UpdateCommentRequestDto

interface CommentService {
    fun createComment(request: CreateCommentRequestDto, postId: Long): CommentResponseDto
    fun updateComment(postId: Long, commentId: Long, request: UpdateCommentRequestDto): CommentResponseDto
    fun deleteComment(commentId:Long)
}