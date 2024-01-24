package com.teamsparta.bunnies.domain.post.model

import com.teamsparta.bunnies.domain.post.dto.request.CreatePostDto
import com.teamsparta.bunnies.domain.post.dto.request.UpdatePostDto
import com.teamsparta.bunnies.domain.user.model.UserEntity
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "post")
class PostEntity private constructor(
    @Column(name = "title")
    var title: String,

    @Column(name = "price")
    var price: Long,

    @Column(name = "description")
    var description: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var author: UserEntity,

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var likes: MutableList<LikeEntity> = mutableListOf()

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long?= null

    @Column(name = "status")
    var status = false

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
            userEntity: UserEntity
        ): PostEntity{
            return PostEntity(
                title = createPostDto.title,
                price = createPostDto.price,
                description = createPostDto.description,
                author = userEntity
            )
        }
    }
}