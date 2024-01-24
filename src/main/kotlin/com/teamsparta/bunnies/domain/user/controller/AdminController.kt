package com.teamsparta.bunnies.domain.user.controller

import com.teamsparta.bunnies.domain.user.dto.request.SignUpRequestDto
import com.teamsparta.bunnies.domain.user.dto.response.UserResponseDto
import com.teamsparta.bunnies.domain.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "admin", description = "관리자 API")
@RequestMapping("/api/admin")
@RestController
class AdminController(
    private val userService: UserService
) {

    @Operation(summary = "관리자 회원가입", description = "관리자 회원가입을 합니다.")
    @PostMapping("/signup")
    fun adminSignUp(
        @Valid
        @RequestBody signUpRequestDto: SignUpRequestDto
    ): ResponseEntity<UserResponseDto> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.adminSignUp(signUpRequestDto))
    }
}