package com.teamsparta.bunnies.domain.user.service

import com.teamsparta.bunnies.domain.user.dto.request.LoginRequestDto
import com.teamsparta.bunnies.domain.user.dto.request.SignUpRequestDto
import com.teamsparta.bunnies.domain.user.dto.response.LoginResponseDto
import com.teamsparta.bunnies.domain.user.dto.response.UserResponseDto

interface UserService {

    fun signUp(request: SignUpRequestDto): UserResponseDto

    fun login(request: LoginRequestDto): LoginResponseDto
}