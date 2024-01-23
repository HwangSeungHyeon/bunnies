package com.teamsparta.bunnies.domain.user.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern

@Schema(description = "회원가입")
data class SignUpRequestDto(

    @field:Email(message = "이메일 형식이 아닙니다.")
    val email: String,//1. 중복검사 + 2. 이메일형식인지 확인

    @field:Pattern(
        regexp = "^[a-zA-Z0-9!@#$%^&*]{8,15}",
        message = "비밀번호는 8~15자, 알파벳 대소문자, 숫자, 특수문자가 포함되어야 합니다.")
    @Schema(description = "비밀번호", example = "Password12!")
    val password: String,// 최소 8자 이상, 15자 이하이며 알파벳 대소문자(az, AZ), 숫자(0~9), 특수문자

    @field:Pattern(
        regexp = "^[a-zA-Z0-9!]{4,10}",
        message = "닉네임은 4~10자 이하, 알파벳 대소문자, 숫자가 포함되어야 합니다.")
    @Schema(description = "닉네임", example = "Nickname12")
    val nickname: String,//최소 4자 이상, 10자 이하이며 알파벳 소문자(az), 숫자(09)

    @Schema(description = "소개")
    val introduction: String?,
    @Schema(description = "주소")
    val address: String?,
    @Schema(description = "번호", example = "010-0000-0000")
    val phone: String?
)
