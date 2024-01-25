package com.teamsparta.bunnies.domain.user.service

import com.teamsparta.bunnies.domain.exception.InvalidCredentialException
import com.teamsparta.bunnies.domain.exception.ModelNotFoundException
import com.teamsparta.bunnies.domain.exception.NotAuthorizationException
import com.teamsparta.bunnies.domain.user.dto.request.ChangePasswordRequestDto
import com.teamsparta.bunnies.domain.user.dto.request.LoginRequestDto
import com.teamsparta.bunnies.domain.user.dto.request.SignUpRequestDto
import com.teamsparta.bunnies.domain.user.dto.request.UpdateUserProfileRequestDto
import com.teamsparta.bunnies.domain.user.dto.response.LoginResponseDto
import com.teamsparta.bunnies.domain.user.dto.response.UserResponseDto
import com.teamsparta.bunnies.domain.user.model.ProfileEntity
import com.teamsparta.bunnies.domain.user.model.UserEntity
import com.teamsparta.bunnies.domain.user.model.UserRole
import com.teamsparta.bunnies.domain.user.model.toResponse
import com.teamsparta.bunnies.domain.user.repository.UserRepository
import com.teamsparta.bunnies.infra.security.UserPrincipal
import com.teamsparta.bunnies.infra.security.jwt.JwtPlugin
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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

    override fun adminSignUp(request: SignUpRequestDto): UserResponseDto {
        if (userRepository.existsByProfileEntityEmail(request.email))
            throw IllegalStateException("사용중인 이메일입니다")

        return userRepository.save(
            UserEntity(

                profileEntity = ProfileEntity(
                    email = request.email,
                    password = passwordEncoder.encode(request.password),
                    nickname = request.nickname,
                    introduction = request.introduction,
                    address = request.address,
                    phone = request.phone),
                role = UserRole.ADMIN
            )
        ).toResponse()
    }

    override fun signUp(request: SignUpRequestDto): UserResponseDto {

        if (userRepository.existsByProfileEntityEmail(request.email))
            throw IllegalStateException("사용중인 이메일입니다")

        return userRepository.save(
            UserEntity(

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

    override fun getUserProfileById(userId: Long): UserResponseDto {
        val user = userRepository.findByIdOrNull(userId)
            ?: throw ModelNotFoundException("User", null)
        return user.toResponse()
    }

    override fun getUserProfile(userPrincipal: UserPrincipal): UserResponseDto {
        val user = userRepository.findByIdOrNull(userPrincipal.id)
            ?: throw ModelNotFoundException("User", null)
        return user.toResponse()
    }

    @Transactional
    override fun updateUserProfile(
        userPrincipal: UserPrincipal,
        updateUserProfileRequestDto: UpdateUserProfileRequestDto,
    ): UserResponseDto {
        val user = userRepository.findByIdOrNull(userPrincipal.id)
            ?: throw ModelNotFoundException("User", null)

        if(!passwordEncoder.matches(updateUserProfileRequestDto.password, user.profileEntity.password))
            throw IllegalStateException("틀린 비밀번호입니다")

        user.profileEntity = ProfileEntity(
            email = updateUserProfileRequestDto.email,
            nickname = updateUserProfileRequestDto.nickname,
            introduction = updateUserProfileRequestDto.introduction,
            address = updateUserProfileRequestDto.address,
            phone = updateUserProfileRequestDto.phone,
            password = user.profileEntity.password
        )

        return userRepository.save(user).toResponse()
    }

    @Transactional
    override fun changePassword(
        userPrincipal: UserPrincipal,
        changePasswordRequestDto: ChangePasswordRequestDto
    ): UserResponseDto {
        val user = userRepository.findByIdOrNull(userPrincipal.id)
            ?: throw ModelNotFoundException("User", null)

        if(!passwordEncoder.matches(changePasswordRequestDto.password, user.profileEntity.password))
            throw IllegalStateException("틀린 비밀번호입니다")

             user.profileEntity.password = passwordEncoder.encode(changePasswordRequestDto.newPassword)

        return userRepository.save(user).toResponse()
    }

    @Transactional
    override fun deleteUserProfile(userPrincipal: UserPrincipal) {
        val user = userRepository.findByIdOrNull(userPrincipal.id)
            ?: throw ModelNotFoundException("User", null)
        if(user.id != userPrincipal.id)throw NotAuthorizationException()
        userRepository.delete(user)
    }
}