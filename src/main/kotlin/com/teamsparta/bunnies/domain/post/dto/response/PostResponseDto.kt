package com.teamsparta.bunnies.domain.post.dto.response

import com.teamsparta.bunnies.domain.post.model.PostEntity
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "Post에 대한 반응을 전달하는 객체")
data class PostResponseDto(
    @Schema(description = "Post PK", example = "0")
    val id: Long,

    @Schema(description = "게시글 제목", example = "제목")
    val title: String,

    @Schema(description = "판매 제품 가격", example = "1000")
    val price: Long,

    @Schema(description = "판매 제품 설명", example = "설명")
    val description: String,

    @Schema(description = "판매 완료 여부 판단", example = "false")
    val status: Boolean,

    @Schema(description = "게시글이 생성된 시간")
    val createdAt: LocalDateTime,

    @Schema(description = "게시글이 수정된 시간")
    val updateAt: LocalDateTime
){
    companion object{
        fun toResponse(
            postEntity: PostEntity
        ): PostResponseDto{
            return PostResponseDto(
                id = postEntity.id!!,
                title = postEntity.title,
                price = postEntity.price,
                description = postEntity.description,
                status = postEntity.status,
                createdAt = postEntity.createdAt!!,
                updateAt = postEntity.updateAt!!
            )
        }
    }
}
