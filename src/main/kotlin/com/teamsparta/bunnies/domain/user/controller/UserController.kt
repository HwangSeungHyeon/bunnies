package com.teamsparta.bunnies.domain.user.controller

import com.teamsparta.bunnies.domain.user.dto.request.LoginRequest
import com.teamsparta.bunnies.domain.user.dto.request.SignUpRequest
import com.teamsparta.bunnies.domain.user.dto.response.LoginResponse
import com.teamsparta.bunnies.domain.user.dto.response.UserResponse
import com.teamsparta.bunnies.domain.user.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.math.sign

@RequestMapping("/api")
@RestController
class UserController(
    private val userService: UserService
) {

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.login(loginRequest))

    }

    @PostMapping("/signup")
    fun signUp(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.signUp(signUpRequest))
    }








}