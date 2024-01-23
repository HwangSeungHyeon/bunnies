package com.teamsparta.bunnies.domain.user.dto.request

data class SignUpRequestDto(
    val email: String,
    val password: String,
    val nickname: String,
    val introduction: String?,
    val address: String?,
    val phone: String?
)