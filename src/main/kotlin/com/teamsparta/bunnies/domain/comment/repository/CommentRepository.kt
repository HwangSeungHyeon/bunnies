package com.teamsparta.bunnies.domain.comment.repository

import com.teamsparta.bunnies.domain.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository


interface CommentRepository : JpaRepository<Comment, Long>{

}