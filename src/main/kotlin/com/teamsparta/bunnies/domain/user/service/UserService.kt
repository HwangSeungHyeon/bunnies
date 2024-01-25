package com.teamsparta.bunnies.domain.user.service

import com.teamsparta.bunnies.domain.user.dto.request.ChangePasswordRequestDto
import com.teamsparta.bunnies.domain.user.dto.request.LoginRequestDto
import com.teamsparta.bunnies.domain.user.dto.request.SignUpRequestDto
import com.teamsparta.bunnies.domain.user.dto.request.UpdateUserProfileRequestDto
import com.teamsparta.bunnies.domain.user.dto.response.LoginResponseDto
import com.teamsparta.bunnies.domain.user.dto.response.UserResponseDto
import com.teamsparta.bunnies.infra.security.UserPrincipal

interface UserService {

    fun adminSignUp(request: SignUpRequestDto): UserResponseDto

    fun signUp(request: SignUpRequestDto): UserResponseDto

    fun login(request: LoginRequestDto): LoginResponseDto

    fun getUserProfileById(userId: Long): UserResponseDto

    fun  getUserProfile(userPrincipal: UserPrincipal): UserResponseDto

    fun updateUserProfile(userPrincipal: UserPrincipal, updateUserProfileRequestDto: UpdateUserProfileRequestDto): UserResponseDto

    fun changePassword(userPrincipal: UserPrincipal, changePasswordRequestDto: ChangePasswordRequestDto): UserResponseDto

    fun deleteUserProfile(userPrincipal: UserPrincipal)
}