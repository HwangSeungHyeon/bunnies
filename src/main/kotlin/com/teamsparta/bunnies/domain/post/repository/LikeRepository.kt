package com.teamsparta.bunnies.domain.post.repository

import com.teamsparta.bunnies.domain.post.model.LikeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface LikeRepository: JpaRepository<LikeEntity, Long> {
    fun existsByPostIdAndUserId(postId: Long, userId: Long): Boolean

    fun deleteByPostIdAndUserId(postId: Long, userId: Long)
}