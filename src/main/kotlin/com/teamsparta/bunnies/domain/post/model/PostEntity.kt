package com.teamsparta.bunnies.domain.post.model

import com.teamsparta.bunnies.domain.post.dto.request.CreatePostDto
import com.teamsparta.bunnies.domain.post.dto.request.UpdatePostDto
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
        status = true
    }

    companion object{
        fun toEntity(
            createPostDto: CreatePostDto
        ): PostEntity{
            return PostEntity(
                title = createPostDto.title,
                price = createPostDto.price,
                description = createPostDto.description
            )
        }
    }
}