package com.teamsparta.bunnies.domain.admin.dto.response

data class AdminResponseDto(
    val email: String,
    val nickname: String,
    val introduction: String,
    val address: String,
    val phone: String,
    val role: String
)
