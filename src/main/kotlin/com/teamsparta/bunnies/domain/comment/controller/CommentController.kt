package com.teamsparta.bunnies.domain.comment.controller

import com.teamsparta.bunnies.domain.comment.dto.CommentResponseDto
import com.teamsparta.bunnies.domain.comment.dto.CreateCommentRequestDto
import com.teamsparta.bunnies.domain.comment.dto.UpdateCommentRequestDto
import com.teamsparta.bunnies.domain.comment.service.CommentService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RequestMapping("/api/posts/{postId}/comments")
@RestController
class CommentController(
        val commentService: CommentService
) {
    @Operation(summary = "comment 생성")
    @PostMapping
    fun createComment(
        @PathVariable postId: Long,
        @RequestBody createCommentRequestDto: CreateCommentRequestDto,
    ): ResponseEntity<CommentResponseDto> {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.createComment(createCommentRequestDto, postId))
    }

    @Operation(summary = "comment 수정")
    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @RequestBody updateCommentRequestDto: UpdateCommentRequestDto
    ): ResponseEntity<CommentResponseDto> {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.updateComment(postId, commentId, updateCommentRequestDto))
    }

    @Operation(summary = "comment 삭제")
    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
    ): ResponseEntity<Unit> {

        commentService.deleteComment(commentId)

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }


}