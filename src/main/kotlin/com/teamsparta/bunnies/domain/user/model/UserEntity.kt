package com.teamsparta.bunnies.domain.user.model

import com.teamsparta.bunnies.domain.user.dto.response.UserResponseDto
import jakarta.persistence.*

@Entity
@Table(name = "app_user")
class User(

    @Embedded
    var profileEntity: ProfileEntity,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val role: UserRole
){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

}

fun User.toResponse(): UserResponseDto {
    return UserResponseDto(
        email = profileEntity.email,
        nickname = profileEntity.nickname,
        introduction = profileEntity.introduction.toString(),
        address = profileEntity.address.toString(),
        phone = profileEntity.phone.toString(),
        role = role.name
    )
}