package com.teamsparta.bunnies.domain.user.dto.response

data class UserResponse(
    val email: String,
    val nickname: String,
    val introduction: String,
    val address: String,
    val phone: String,
    val role: String
)