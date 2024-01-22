package com.teamsparta.bunnies.domain.user.service

import com.teamsparta.bunnies.domain.exception.InvalidCredentialException
import com.teamsparta.bunnies.domain.exception.ModelNotFoundException
import com.teamsparta.bunnies.domain.user.dto.request.LoginRequest
import com.teamsparta.bunnies.domain.user.dto.request.SignUpRequest
import com.teamsparta.bunnies.domain.user.dto.response.LoginResponse
import com.teamsparta.bunnies.domain.user.dto.response.UserResponse
import com.teamsparta.bunnies.domain.user.model.Profile
import com.teamsparta.bunnies.domain.user.model.User
import com.teamsparta.bunnies.domain.user.model.UserRole
import com.teamsparta.bunnies.domain.user.model.toResponse
import com.teamsparta.bunnies.domain.user.repository.UserRepository
import com.teamsparta.bunnies.infra.security.jwt.JwtPlugin
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
): UserService {

    override fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findByProfileEmail(request.email) ?: throw ModelNotFoundException("User", null) //이메일 체크

        if (!passwordEncoder.matches(request.password, user.profile.password)) {
            throw InvalidCredentialException()
        }

        return LoginResponse(
            accessToken = jwtPlugin.generateAccessToken(
                subject = user.id.toString(),
                email = user.profile.email,
                role = user.role.name
            )
        )
    }

    override fun signUp(request: SignUpRequest): UserResponse {

        if (userRepository.existsByProfileEmail(request.email))
            throw IllegalStateException("사용중")
        return userRepository.save(
            User(

                profile = Profile(
                    email = request.email,
                    password = passwordEncoder.encode(request.password),
                    nickname = request.nickname,
                    introduction = request.introduction,
                    address = request.address,
                    phone = request.phone),
                    role = UserRole.USER
                )
            ).toResponse()
    }
}