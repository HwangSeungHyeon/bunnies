package com.teamsparta.newsfeed.domain.comment.model

import com.teamsparta.newsfeed.domain.BaseTimeEntity
import com.teamsparta.newsfeed.domain.article.model.Article
import com.teamsparta.newsfeed.domain.comment.dto.CommentResponseDto
import jakarta.persistence.*
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