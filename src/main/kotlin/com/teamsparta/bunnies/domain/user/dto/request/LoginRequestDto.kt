package com.teamsparta.bunnies.domain.user.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern

@Schema(description = "로그인")
data class LoginRequestDto(

    @field:Email(message = "이메일 형식이 아닙니다.")
    @Schema(description = "이메일", example = "email@email.com")
    val email: String,

    @field:Pattern(
        regexp = "^[a-zA-Z0-9!@#$%^&*]{8,15}",
        message = "비밀번호는 8~15자, 알파벳 대소문자, 숫자, 특수문자가 포함되어야 합니다.")
    @Schema(description = "비밀번호", example = "Password12!")
    val password: String
)