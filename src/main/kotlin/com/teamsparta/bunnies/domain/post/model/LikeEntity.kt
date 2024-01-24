package com.teamsparta.bunnies.domain.post.model

import com.teamsparta.bunnies.domain.user.model.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "like_post")
class LikeEntity private constructor(
    @ManyToOne
    @JoinColumn(name = "post_id")
    var post: PostEntity,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: UserEntity
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object{
        fun toEntity(
            postEntity: PostEntity,
            userEntity: UserEntity
        ): LikeEntity{
            return LikeEntity(
                post = postEntity,
                user = userEntity
            )
        }
    }
}