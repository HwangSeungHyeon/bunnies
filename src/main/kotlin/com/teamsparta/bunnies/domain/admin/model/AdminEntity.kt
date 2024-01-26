package com.teamsparta.bunnies.domain.admin.model

import com.teamsparta.bunnies.domain.admin.dto.response.AdminResponseDto
import jakarta.persistence.*

@Entity
@Table(name = "app_user2")
class AdminEntity(

    @Embedded
    var adminProfileEntity: AdminProfileEntity,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val role: AdminRole,
){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

}

fun AdminEntity.toResponse(): AdminResponseDto {
    return AdminResponseDto(
        email = adminProfileEntity.email,
        nickname = adminProfileEntity.nickname,
        introduction = adminProfileEntity.introduction.toString(),
        address = adminProfileEntity.address.toString(),
        phone = adminProfileEntity.phone.toString(),
        role = role.name
    )
}