package com.teamsparta.bunnies.domain.comment.dto


data class CreateCommentRequestDto(
        val comment: String,
        val name: String,
)