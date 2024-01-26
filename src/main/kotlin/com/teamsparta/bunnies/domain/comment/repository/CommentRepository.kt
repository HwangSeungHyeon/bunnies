package com.teamsparta.bunnies.domain.comment.repository

import com.teamsparta.bunnies.domain.comment.model.CommentEntity
import org.springframework.data.jpa.repository.JpaRepository


interface CommentRepository : JpaRepository<CommentEntity, Long>{

    fun findAllByUserId (userId: Long): List<CommentEntity>?
}