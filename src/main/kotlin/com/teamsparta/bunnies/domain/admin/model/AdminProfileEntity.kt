package com.teamsparta.bunnies.domain.admin.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class AdminProfileEntity(
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