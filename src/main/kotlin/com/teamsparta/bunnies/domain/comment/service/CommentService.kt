package com.teamsparta.newsfeed.domain.comment.service

import com.teamsparta.newsfeed.domain.comment.dto.CommentResponseDto
import com.teamsparta.newsfeed.domain.comment.dto.CreateCommentRequestDto
import com.teamsparta.newsfeed.domain.comment.dto.DeleteCommentRequestDto
import com.teamsparta.newsfeed.domain.comment.dto.UpdateCommentRequestDto

interface CommentService {
    fun createComment(request: CreateCommentRequestDto, articleId: Long): CommentResponseDto
    fun updateComment(request: UpdateCommentRequestDto): CommentResponseDto
    fun deleteComment(request: DeleteCommentRequestDto)
}