package com.teamsparta.bunnies.domain.user.model

import com.teamsparta.bunnies.domain.comment.model.CommentEntity
import com.teamsparta.bunnies.domain.post.model.LikeEntity
import com.teamsparta.bunnies.domain.post.model.PostEntity
import com.teamsparta.bunnies.domain.user.dto.response.UserResponseDto
import jakarta.persistence.*

@Entity
@Table(name = "app_user2")
class UserEntity(

    @Embedded
    var profileEntity: ProfileEntity,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var role: UserRole,
//
//    @OneToMany(mappedBy = "app_user", fetch = FetchType.LAZY, orphanRemoval = true)
//    var post: MutableList<PostEntity> = mutableListOf(),
//
//    @OneToMany(mappedBy = "app_user", fetch = FetchType.LAZY, orphanRemoval = true)
//    var comment: MutableList<CommentEntity> = mutableListOf(),
//
//    @OneToMany(mappedBy = "app_user", fetch = FetchType.LAZY, orphanRemoval = true)
//    var like: MutableList<LikeEntity> = mutableListOf()
){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

}

fun UserEntity.toResponse(): UserResponseDto {
    return UserResponseDto(
        email = profileEntity.email,
        nickname = profileEntity.nickname,
        introduction = profileEntity.introduction.toString(),
        address = profileEntity.address.toString(),
        phone = profileEntity.phone.toString(),
        role = role.name
    )
}