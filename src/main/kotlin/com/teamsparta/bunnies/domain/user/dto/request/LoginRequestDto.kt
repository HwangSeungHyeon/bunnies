package com.teamsparta.bunnies.domain.user.dto.request

data class LoginRequestDto(
    val email: String,
    val password: String
)