package com.teamsparta.newsfeed.domain.comment.dto

data class CommentResponseDto(
        val id: Long?,
        val comment: String,
        val name: String,
)
