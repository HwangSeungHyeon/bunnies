package com.teamsparta.bunnies.domain.comment.dto

data class UpdateCommentRequestDto(
        val id: Long,
        val comment: String,
        val name: String,
)