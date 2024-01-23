package com.teamsparta.bunnies.domain.user.model

import com.teamsparta.bunnies.domain.user.dto.request.UpdateUserProfileRequestDto
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.springframework.security.crypto.password.PasswordEncoder

@Embeddable
class ProfileEntity(
    @Column(name = "email")
    var email: String,

    @Column(name = "password")
    var password: String,

    @Column(name = "nickname")
    var nickname: String,

    @Column(name = "introduction")
    var introduction: String?,

    @Column(name = "address")
    var address: String?,

    @Column(name = "phone")
    var phone: String?
)