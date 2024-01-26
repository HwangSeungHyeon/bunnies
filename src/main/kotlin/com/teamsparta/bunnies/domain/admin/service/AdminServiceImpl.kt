package com.teamsparta.bunnies.domain.admin.service

import com.teamsparta.bunnies.domain.admin.dto.request.AdminLoginRequestDto
import com.teamsparta.bunnies.domain.admin.dto.request.AdminSignUpRequestDto
import com.teamsparta.bunnies.domain.admin.dto.request.UpdateAdminProfileRequestDto
import com.teamsparta.bunnies.domain.admin.dto.request.ChangeUserRoleRequestDto
import com.teamsparta.bunnies.domain.admin.dto.response.AdminLoginResponseDto
import com.teamsparta.bunnies.domain.admin.model.AdminEntity
import com.teamsparta.bunnies.domain.admin.model.toResponse
import com.teamsparta.bunnies.domain.admin.repository.AdminRepository
import com.teamsparta.bunnies.domain.admin.dto.response.AdminResponseDto
import com.teamsparta.bunnies.domain.admin.model.AdminProfileEntity
import com.teamsparta.bunnies.domain.admin.model.AdminRole
import com.teamsparta.bunnies.domain.comment.repository.CommentRepository
import com.teamsparta.bunnies.domain.user.model.ProfileEntity
import com.teamsparta.bunnies.domain.exception.InvalidCredentialException
import com.teamsparta.bunnies.domain.exception.ModelNotFoundException
import com.teamsparta.bunnies.domain.post.repository.PostRepository
import com.teamsparta.bunnies.domain.user.dto.request.UpdateUserProfileRequestDto
import com.teamsparta.bunnies.domain.user.dto.response.UserResponseDto
import com.teamsparta.bunnies.domain.user.model.UserRole
import com.teamsparta.bunnies.domain.user.model.toResponse
import com.teamsparta.bunnies.domain.user.repository.UserRepository
import com.teamsparta.bunnies.infra.security.UserPrincipal
import com.teamsparta.bunnies.infra.security.jwt.JwtPlugin
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.Model

@Service
class AdminServiceImpl(
    private val adminRepository: AdminRepository,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
    private val jwtPlugin: JwtPlugin
): AdminService {

    //관리자 회원가입
    override fun adminSignUp(request: AdminSignUpRequestDto): AdminResponseDto{
        if (adminRepository.existsByAdminProfileEntityEmail(request.email))
            throw IllegalStateException("사용중인 이메일입니다")

        return adminRepository.save(
            AdminEntity(

                adminProfileEntity = AdminProfileEntity(
                    email = request.email,
                    password = passwordEncoder.encode(request.password),
                    nickname = request.nickname,
                    introduction = request.introduction,
                    address = request.address,
                    phone = request.phone),
                role = AdminRole.ADMIN
            )
        ).toResponse()
    }

    //관리자 로그인
    override fun adminLogin(request: AdminLoginRequestDto): AdminLoginResponseDto{
        val admin = adminRepository.findByAdminProfileEntityEmail(request.email)
            ?: throw ModelNotFoundException("User", null) //이메일 체크

        if(admin.role.toString() != "ADMIN")
            throw InvalidCredentialException("옳지않은 접근입니다.")

        if (!passwordEncoder.matches(request.password, admin.adminProfileEntity.password)) {
            throw InvalidCredentialException("비밀번호가 일치하지 않습니다.")
        }

        return AdminLoginResponseDto(
            accessToken = jwtPlugin.generateAccessToken(
                subject = admin.id.toString(),
                email = admin.adminProfileEntity.email,
                role = admin.role.name
            )
        )
    }

    //모든 유저 프로필 조회
    @Transactional(readOnly = true)
    override fun getAllUserProfile(): List<UserResponseDto> {

        return userRepository.findAll().map { it.toResponse() }
    }


    //관리자 본인 프로필 수정
    @Transactional
    override fun updateAdminProfile(
        userPrincipal: UserPrincipal,
        updateAdminProfileRequestDto: UpdateAdminProfileRequestDto,
    ): AdminResponseDto {
        val admin = adminRepository.findByIdOrNull(userPrincipal.id)
            ?: throw ModelNotFoundException("User", null)

        if(!passwordEncoder.matches(updateAdminProfileRequestDto.password, admin.adminProfileEntity.password))
            throw IllegalStateException("비밀번호가 일치하지 않습니다.")

        admin.adminProfileEntity = AdminProfileEntity(
            email = updateAdminProfileRequestDto.email,
            nickname = updateAdminProfileRequestDto.nickname,
            introduction = updateAdminProfileRequestDto.introduction,
            address = updateAdminProfileRequestDto.address,
            phone = updateAdminProfileRequestDto.phone,
            password = admin.adminProfileEntity.password
        )

        return adminRepository.save(admin).toResponse()
    }

    //회원 프로필 수정
    @Transactional
    override fun updateUserIdProfile(
        userPrincipal: UserPrincipal,
        userId: Long,
        updateUserProfileRequestDto: UpdateUserProfileRequestDto
    ): UserResponseDto{
        val user = userRepository.findByIdOrNull(userId)
            ?: throw ModelNotFoundException("User", null)

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


    //회원 삭제
    @Transactional
    override fun deleteUserId(
        userPrincipal: UserPrincipal,
        userId: Long
    ){
        val user = userRepository.findByIdOrNull(userId)
            ?: throw ModelNotFoundException("User", null)

        val postList = postRepository.findAllByUserId(user.id!!)
            ?: throw ModelNotFoundException("Post", user.id)

        postRepository.deleteAll(postList)

        val commentList = commentRepository.findAllByUserId(user.id!!)
            ?:throw ModelNotFoundException("Comment", user.id)

        commentRepository.deleteAll(commentList)

        userRepository.delete(user)
    }

    //회원 역할 변경
    @Transactional
    override fun changeUserRole(
        userPrincipal: UserPrincipal,
        userId: Long,
        changeUserRoleRequestDto: ChangeUserRoleRequestDto
    ): UserResponseDto{
        val user = userRepository.findByIdOrNull(userId)
            ?: throw ModelNotFoundException("User", null)

        when(changeUserRoleRequestDto.role) {
            "ADMIN" -> user.role = UserRole.valueOf("ADMIN")
            "USER" -> user.role = UserRole.valueOf("USER")
        }
        return userRepository.save(user).toResponse()
    }
}