package com.teamsparta.bunnies.domain.user.controller

import com.teamsparta.bunnies.domain.user.dto.request.ChangePasswordRequestDto
import com.teamsparta.bunnies.domain.user.dto.request.LoginRequestDto
import com.teamsparta.bunnies.domain.user.dto.request.SignUpRequestDto
import com.teamsparta.bunnies.domain.user.dto.request.UpdateUserProfileRequestDto
import com.teamsparta.bunnies.domain.user.dto.response.LoginResponseDto
import com.teamsparta.bunnies.domain.user.dto.response.UserResponseDto
import com.teamsparta.bunnies.domain.user.service.UserService
import com.teamsparta.bunnies.infra.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "users", description = "사용자 API")
@RequestMapping("/api")
@RestController
class UserController(
    private val userService: UserService
) {

    @Operation(summary = "로그인", description = "로그인을 합니다.")
    @PostMapping("/login")
    fun login(
        @RequestBody loginRequestDto: LoginRequestDto
    ): ResponseEntity<LoginResponseDto> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.login(loginRequestDto))

    }

    @Operation(summary = "사용자 회원가입", description = "사용자 회원가입을 합니다.")
    @PostMapping("/signup")
    fun signUp(
        @Valid
        @RequestBody signUpRequestDto: SignUpRequestDto
    ): ResponseEntity<UserResponseDto> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.signUp(signUpRequestDto))
    }

    @Operation(summary = "타인 프로필 조회", description = "타인 프로필 조회 합니다.")
    @GetMapping("/users/{userid}/profiles")
    fun getUserProfileById(
        @PathVariable userid: Long
    ): ResponseEntity<UserResponseDto> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.getUserProfileById(userid))
    }

    @Operation(summary = "본인 프로필 조회", description = "본인 프로필 조회 합니다.")
    @GetMapping("/users/{userid}/profile")
    fun getUserProfile(
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<UserResponseDto> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.getUserProfile(userPrincipal))
    }

    @Operation(summary = "본인 프로필 수정", description = "본인 프로필 수정 합니다.")
    @PutMapping("/users/{userid}")
    fun updateUserProfile(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @Valid
        @RequestBody updateUserProfileRequestDto: UpdateUserProfileRequestDto
    ): ResponseEntity<UserResponseDto> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.updateUserProfile(userPrincipal, updateUserProfileRequestDto))
    }

    @Operation(summary = "비밀번호 변경")
    @PatchMapping("/users/{userid}")
    fun changePassword(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @Valid
        @RequestBody changePasswordRequestDto: ChangePasswordRequestDto
    ): ResponseEntity<UserResponseDto> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.changePassword(userPrincipal, changePasswordRequestDto))
    }

    @Operation(summary = "회원 탈퇴", description = "사용자 삭제")
    @DeleteMapping("/users/{userid}")
    fun deleteUserProfile(
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<Unit> {
        userService.deleteUserProfile(userPrincipal)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }





}