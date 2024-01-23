package com.teamsparta.bunnies.domain.user.repository

import com.teamsparta.bunnies.domain.user.model.ProfileEntity
import com.teamsparta.bunnies.domain.user.model.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository <UserEntity, Long> {

    fun existsByProfileEntityEmail(email: String): Boolean

    fun findByProfileEntityEmail(email:String) : UserEntity?

    fun findByProfileEntityNickname(nickname: String): UserEntity?

//    fun findTop3ByProfileEntityPasswordOrderBy(profileEntity: ProfileEntity):
}