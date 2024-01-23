package com.teamsparta.bunnies.domain.comment.service

import com.teamsparta.bunnies.domain.comment.dto.CommentResponseDto
import com.teamsparta.bunnies.domain.comment.dto.CreateCommentRequestDto
import com.teamsparta.bunnies.domain.comment.dto.DeleteCommentRequestDto
import com.teamsparta.bunnies.domain.comment.dto.UpdateCommentRequestDto

interface CommentService {
    fun createComment(request: CreateCommentRequestDto, articleId: Long): CommentResponseDto
    fun updateComment(request: UpdateCommentRequestDto): CommentResponseDto
    fun deleteComment(request: DeleteCommentRequestDto)
}