package com.teamsparta.bunnies.domain.admin.controller

import com.teamsparta.bunnies.domain.admin.dto.request.AdminLoginRequestDto
import com.teamsparta.bunnies.domain.admin.dto.request.AdminSignUpRequestDto
import com.teamsparta.bunnies.domain.admin.dto.request.ChangeUserRoleRequestDto
import com.teamsparta.bunnies.domain.admin.dto.response.AdminLoginResponseDto
import com.teamsparta.bunnies.domain.admin.dto.response.AdminResponseDto
import com.teamsparta.bunnies.domain.admin.service.AdminService
import com.teamsparta.bunnies.domain.user.dto.request.UpdateUserProfileRequestDto
import com.teamsparta.bunnies.domain.user.dto.response.UserResponseDto
import com.teamsparta.bunnies.domain.user.service.UserService
import com.teamsparta.bunnies.infra.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@Tag(name = "admin", description = "관리자 API")
@RequestMapping("/api/admins")
@RestController
class AdminController(
    private val adminService: AdminService,
    private val userService: UserService
) {

    @PreAuthorize("isAnonymous()")
    @Operation(summary = "관리자 회원가입", description = "관리자 회원가입을 합니다.")
    @PostMapping("/signup")
    fun adminSignUp(
        @Valid
        @RequestBody adminSignUpRequestDto: AdminSignUpRequestDto
    ): ResponseEntity<AdminResponseDto> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(adminService.adminSignUp(adminSignUpRequestDto))
    }

    @PreAuthorize("isAnonymous()")
    @Operation(summary = "관리자 로그인", description = "관리자 로그인을 합니다.")
    @PostMapping("/login")
    fun login(
        @RequestBody adminLoginRequestDto: AdminLoginRequestDto
    ): ResponseEntity<AdminLoginResponseDto> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(adminService.adminLogin(adminLoginRequestDto))

    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "유저 프로필 수정", description = "유저 프로필 수정 합니다.")
    @PutMapping("/users/{userId}/profile")
    fun updateUserIdProfile(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable userId: Long,
        @RequestBody updateUserProfileRequestDto: UpdateUserProfileRequestDto
    ): ResponseEntity<UserResponseDto> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(adminService.updateUserIdProfile(userPrincipal, userId, updateUserProfileRequestDto))
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "유저 프로필 삭제", description = "유저 프로필 삭제 합니다.")
    @DeleteMapping("/users/{userId}")
    fun deleteUserId(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable userId: Long
    ): ResponseEntity<Unit> {
        adminService.deleteUserId(userPrincipal, userId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }


    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "유저 역할 변경", description = "유저의 역할을 유저 또는 어드민으로 변경합니다.")
    @PatchMapping("/users/{userId}/profile")
    fun changeUserRole(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable userId: Long,
        @RequestBody changeUserRoleRequestDto: ChangeUserRoleRequestDto
    ): ResponseEntity<UserResponseDto> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(adminService.changeUserRole(userPrincipal, userId, changeUserRoleRequestDto))
    }








}