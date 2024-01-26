package com.teamsparta.bunnies.domain.post.model

import com.teamsparta.bunnies.domain.comment.model.CommentEntity
import com.teamsparta.bunnies.domain.exception.InvalidCredentialException
import com.teamsparta.bunnies.domain.post.dto.request.CreatePostDto
import com.teamsparta.bunnies.domain.post.dto.request.UpdatePostDto
import com.teamsparta.bunnies.domain.user.model.UserEntity
import com.teamsparta.bunnies.infra.security.UserPrincipal
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "post2")
class PostEntity private constructor(
    @Column(name = "title")
    var title: String,

    @Column(name = "price")
    var price: Long,

    @Column(name = "description")
    var description: String,

    @Column(name = "user_id")
    var userId: Long,
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    var user: UserEntity,

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, orphanRemoval = true)
    val commentEntity: MutableList<CommentEntity> = mutableListOf(),

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, orphanRemoval = true)
    var likes: MutableList<LikeEntity> = mutableListOf()

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long?= null

    @Column(name = "status")
    var status = false
    //choi

    @CreatedDate
    @Column(name = "create_at")
    var createdAt: LocalDateTime? = null

    @LastModifiedDate
    @Column(name = "update_at")
    var updateAt: LocalDateTime? = null

    fun update(updatePostDto: UpdatePostDto){
        title = updatePostDto.title
        price = updatePostDto.price
        description = updatePostDto.description
    }

    fun isComplete(){
        status = !status
    }

    companion object{
        fun toEntity(
            createPostDto: CreatePostDto,
            userPrincipal: UserPrincipal
        ): PostEntity{
            return PostEntity(
                title = createPostDto.title,
                price = createPostDto.price,
                description = createPostDto.description,
                userId = userPrincipal.id
            )
        }

        fun checkPostPermission(
            userPrincipal: UserPrincipal,
            post: PostEntity
        ) {
            if ((userPrincipal.id != post.userId) && (userPrincipal.authorities.first().toString() == "ROLE_USER"))
                throw InvalidCredentialException("본인의 글이 아니므로 권한이 없습니다.")
        }
    }
}