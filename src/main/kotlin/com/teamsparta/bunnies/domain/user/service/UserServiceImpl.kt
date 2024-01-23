package com.teamsparta.bunnies.domain.user.service

import com.teamsparta.bunnies.domain.exception.InvalidCredentialException
import com.teamsparta.bunnies.domain.exception.ModelNotFoundException
import com.teamsparta.bunnies.domain.user.dto.request.LoginRequestDto
import com.teamsparta.bunnies.domain.user.dto.request.SignUpRequestDto
import com.teamsparta.bunnies.domain.user.dto.response.LoginResponseDto
import com.teamsparta.bunnies.domain.user.dto.response.UserResponseDto
import com.teamsparta.bunnies.domain.user.model.ProfileEntity
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

    override fun login(request: LoginRequestDto): LoginResponseDto {
        val user = userRepository.findByProfileEntityEmail(request.email)
            ?: throw ModelNotFoundException("User", null) //이메일 체크

        if (!passwordEncoder.matches(request.password, user.profileEntity.password)) {
            throw InvalidCredentialException()
        }

        return LoginResponseDto(
            accessToken = jwtPlugin.generateAccessToken(
                subject = user.id.toString(),
                email = user.profileEntity.email,
                role = user.role.name
            )
        )
    }

    override fun signUp(request: SignUpRequestDto): UserResponseDto {

        if (userRepository.existsByProfileEntityEmail(request.email))
            throw IllegalStateException("사용중인 이메일입니다")

        return userRepository.save(
            User(

                profileEntity = ProfileEntity(
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