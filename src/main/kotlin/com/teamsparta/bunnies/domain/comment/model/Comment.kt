package com.teamsparta.bunnies.domain.comment.model

import com.teamsparta.bunnies.domain.comment.dto.CommentResponseDto
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "comments")
class Comment(
        @Column(name = "comment")
        var comment: String,
        @Column(name = "name")
        var name: String,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "articleId")
        val article: Article

) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun checkAuthentication(name: String) {
        if (name != this.name) {
            throw Exception("wrong authentication for comment")
        }
    }
}

fun Comment.toResponse(): CommentResponseDto {
    return CommentResponseDto(
            id = id!!,
            comment = comment,
            name = name,
    )
}