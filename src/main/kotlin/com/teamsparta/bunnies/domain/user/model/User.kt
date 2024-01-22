package com.teamsparta.bunnies.domain.user.model

import com.teamsparta.bunnies.domain.user.dto.response.UserResponse
import jakarta.persistence.*
import org.springframework.data.jpa.domain.AbstractPersistable_.id

@Entity
@Table(name = "app_user")
class User(

    @Embedded
    var profile: Profile,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: UserRole = UserRole.USER
){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

}

fun User.toResponse(): UserResponse {
    return UserResponse(
        email = profile.email,
        nickname = profile.nickname,
        introduction = profile.introduction.toString(),
        address = profile.address.toString(),
        phone = profile.phone.toString(),
        role = role.name
    )
}