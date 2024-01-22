package com.teamsparta.bunnies.domain.user.repository

import com.teamsparta.bunnies.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository <User, Long> {

    fun existsByProfileEmail(email: String): Boolean

    fun findByProfileEmail(email:String) : User?
}