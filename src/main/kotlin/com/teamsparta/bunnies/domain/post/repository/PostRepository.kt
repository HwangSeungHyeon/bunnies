package com.teamsparta.bunnies.domain.post.repository

import com.teamsparta.bunnies.domain.post.dto.response.PostResponse
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<PostResponse, Long>{
}