package com.teamsparta.bunnies.domain.comment.model

import com.teamsparta.bunnies.domain.comment.dto.CommentResponseDto
import com.teamsparta.bunnies.domain.exception.InvalidCredentialException
import com.teamsparta.bunnies.domain.post.model.PostEntity
import com.teamsparta.bunnies.infra.security.UserPrincipal
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "comment")
class Comment(
        @Column(name = "comment")
        var comment: String,

        @Column(name = "user_id")
        var userId: Long,

        @ManyToOne(fetch = FetchType.LAZY)// JPA(Java Persistence API)에서 "Basic" 타입으로 지정된 필드에 대해 "Persistence Entity"사용불가
        @JoinColumn(name = "postId")
        val post: PostEntity
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @CreatedDate
    @Column(name = "create_at")
    var createdAt: LocalDateTime? = null

    @LastModifiedDate
    @Column(name = "update_at")
    var updateAt: LocalDateTime? = null

//    fun checkAuthentication(name: String) {
//        if (name != this.name) {
//            throw Exception("wrong authentication for comment")
//        }
//    }
}

fun Comment.toResponse(): CommentResponseDto {
    return CommentResponseDto(
        id = id!!,
        comment = comment,
        //         name = name,
    )
}

fun checkCommentPermission(
        userPrincipal: UserPrincipal,
        foundComment: Comment
    ) {
        if ((userPrincipal.id != foundComment.userId) && (userPrincipal.authorities.first().toString() == "ROLE_USER"))
            throw InvalidCredentialException("본인의 글이 아니므로 권한이 없습니다.")
    }
