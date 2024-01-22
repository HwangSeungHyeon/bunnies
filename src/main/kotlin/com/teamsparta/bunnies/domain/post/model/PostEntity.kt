package com.teamsparta.bunnies.domain.post.model

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name = "post")
class PostEntity private constructor(
    @Column(name = "title")
    val title: String,

    @Column(name = "price")
    val price: Long,

    @Column(name = "description")
    val description: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    var postId: Long?= null

    @Column(name = "status")
    var status = false

    @CreatedDate
    @Column(name = "create_at")
    var createdAt: LocalDateTime? = null

    @LastModifiedDate
    @Column(name = "update_at")
    var updateAt: LocalDateTime? = null
}