package com.teamsparta.bunnies.domain.user.service

import com.teamsparta.bunnies.domain.user.dto.request.LoginRequest
import com.teamsparta.bunnies.domain.user.dto.request.SignUpRequest
import com.teamsparta.bunnies.domain.user.dto.response.LoginResponse
import com.teamsparta.bunnies.domain.user.dto.response.UserResponse

interface UserService {

    fun signUp(request: SignUpRequest): UserResponse

    fun login(request: LoginRequest): LoginResponse
}