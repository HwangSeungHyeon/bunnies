package com.teamsparta.bunnies.domain.user.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class Profile(
    @Column(name = "email", nullable = false)
    var email: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "nickname", nullable = false)
    var nickname: String,

    @Column(name = "introduction")
    var introduction: String?,

    @Column(name = "address")
    var address: String?,

    @Column(name = "phone")
    var phone: String?
)