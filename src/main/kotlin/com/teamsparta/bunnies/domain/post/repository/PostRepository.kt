package com.teamsparta.bunnies.domain.post.repository

import com.teamsparta.bunnies.domain.post.model.PostEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<PostEntity, Long>{

    fun findAllBy(pageable: Pageable): List<PostEntity>

}