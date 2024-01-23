package com.teamsparta.bunnies.domain.user.repository

import com.teamsparta.bunnies.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository <User, Long> {

    fun existsByProfileEntityEmail(email: String): Boolean

    fun findByProfileEntityEmail(email:String) : User?
}