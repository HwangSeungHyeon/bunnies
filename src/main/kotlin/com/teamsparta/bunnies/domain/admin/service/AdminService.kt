package com.teamsparta.bunnies.domain.admin.service

import com.teamsparta.bunnies.domain.admin.dto.request.AdminLoginRequestDto
import com.teamsparta.bunnies.domain.admin.dto.request.AdminSignUpRequestDto
import com.teamsparta.bunnies.domain.admin.dto.request.ChangeUserRoleRequestDto
import com.teamsparta.bunnies.domain.admin.dto.request.UpdateAdminProfileRequestDto
import com.teamsparta.bunnies.domain.admin.dto.response.AdminLoginResponseDto
import com.teamsparta.bunnies.domain.admin.dto.response.AdminResponseDto
import com.teamsparta.bunnies.domain.user.dto.request.UpdateUserProfileRequestDto
import com.teamsparta.bunnies.domain.user.dto.response.UserResponseDto
import com.teamsparta.bunnies.infra.security.UserPrincipal

interface AdminService {

    fun adminSignUp(request: AdminSignUpRequestDto): AdminResponseDto

    fun adminLogin(request: AdminLoginRequestDto): AdminLoginResponseDto

    fun getAllUserProfile(): List<UserResponseDto>

    fun updateAdminProfile(userPrincipal: UserPrincipal, updateAdminProfileRequestDto: UpdateAdminProfileRequestDto): AdminResponseDto

    fun updateUserIdProfile(userPrincipal: UserPrincipal, userId: Long, updateUserProfileRequestDto: UpdateUserProfileRequestDto): UserResponseDto

    fun deleteUserId(userPrincipal: UserPrincipal, userId: Long)

    fun changeUserRole(userPrincipal: UserPrincipal, userId: Long, changeUserRoleRequestDto: ChangeUserRoleRequestDto): UserResponseDto

}

//interface UserService {
//
//    fun signUp(request: SignUpRequestDto): UserResponseDto
//
//    fun login(request: LoginRequestDto): LoginResponseDto
//
//    fun getUserProfileById(userId: Long): UserResponseDto
//
//    fun  getUserProfile(userPrincipal: UserPrincipal): UserResponseDto
//
//    fun updateUserProfile(userPrincipal: UserPrincipal, updateUserProfileRequestDto: UpdateUserProfileRequestDto): UserResponseDto
//
//    fun changePassword(userPrincipal: UserPrincipal, changePasswordRequestDto: ChangePasswordRequestDto): UserResponseDto
//
//    fun deleteUserProfile(userPrincipal: UserPrincipal)
//}