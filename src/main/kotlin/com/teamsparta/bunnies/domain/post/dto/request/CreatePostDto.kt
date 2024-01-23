package com.teamsparta.bunnies.domain.post.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

@Schema(description = "게시글을 작성할 때 입력한 정보를 전달하는 객체")
data class CreatePostDto(
    @field:NotBlank(message = "제목을 입력해주세요.")
    @Size(min = 1, max = 100, message = "제목은 1자 이상 100자 이하로 작성해주세요")
    @Schema(description = "제목 내용", example = "전공책 팝니다.")
    val title: String,

    @field:Positive(message = "가격을 입력해주세요.")
    @Schema(description = "상품 가격", example = "10000")
    val price: Long,

    @field:NotBlank(message = "게시글을 입력해주세요.")
    @Size(min = 1, max = 1000, message = "게시글은 1자 이상 1000자 이하로 작성해주세요")
    @Schema(description = "게시글 내용", example = "한번도 안 본 새제품입니다.")
    val description: String
)
