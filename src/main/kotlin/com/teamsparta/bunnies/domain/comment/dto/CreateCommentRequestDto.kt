package com.teamsparta.newsfeed.domain.comment.dto


data class CreateCommentRequestDto(
        val comment: String,
        val name: String,
)