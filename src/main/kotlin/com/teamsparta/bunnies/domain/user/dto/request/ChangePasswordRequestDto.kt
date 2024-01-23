package com.teamsparta.bunnies.domain.user.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Pattern

data class ChangePasswordRequestDto(
    @Schema(description = "현재 비밀번호", example = "Password12!")
    val password: String,
    @field:Pattern(
        regexp = "^[a-zA-Z0-9!@#$%^&*]{8,15}",
        message = "비밀번호는 8~15자, 알파벳 대소문자, 숫자, 특수문자가 포함되어야 합니다.")
    @Schema(description = "새 비밀번호", example = "Password34!@")
    val newPassword: String,// 최소 8자 이상, 15자 이하이며 알파벳 대소문자(az, AZ), 숫자(0~9), 특수문자
)
