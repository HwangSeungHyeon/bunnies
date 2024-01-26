package com.teamsparta.bunnies.domain.comment.dto

import java.time.LocalDateTime

data class CommentResponseDto(
        val id: Long?,
        val comment: String,
        val createAt: LocalDateTime,
        val updateAt: LocalDateTime
)
