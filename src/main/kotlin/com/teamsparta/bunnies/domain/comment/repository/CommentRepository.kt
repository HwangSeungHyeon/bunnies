package com.teamsparta.newsfeed.domain.comment.repository

import com.teamsparta.newsfeed.domain.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository


interface CommentRepository : JpaRepository<Comment, Long>