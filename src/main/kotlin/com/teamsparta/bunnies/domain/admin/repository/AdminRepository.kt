package com.teamsparta.bunnies.domain.admin.repository

import com.teamsparta.bunnies.domain.admin.model.AdminEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepository: JpaRepository<AdminEntity, Long> {

    fun existsByAdminProfileEntityEmail(email: String): Boolean

    fun findByAdminProfileEntityEmail(email:String) : AdminEntity?

}