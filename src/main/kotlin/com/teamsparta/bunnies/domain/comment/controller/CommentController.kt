package com.teamsparta.newsfeed.domain.comment.controller

import com.teamsparta.newsfeed.domain.comment.dto.CommentResponseDto
import com.teamsparta.newsfeed.domain.comment.dto.CreateCommentRequestDto
import com.teamsparta.newsfeed.domain.comment.dto.DeleteCommentRequestDto
import com.teamsparta.newsfeed.domain.comment.dto.UpdateCommentRequestDto
import com.teamsparta.newsfeed.domain.comment.service.CommentService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*


@RequestMapping("/articles/{articleId}/comments")
@RestController
class CommentController(
        val commentService: CommentService
) {
    @Operation(summary = "comment 생성")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEMBER')")
    fun createComment(
        @PathVariable articleId: Long,
        @RequestBody createCommentRequestDto: CreateCommentRequestDto,
    ): ResponseEntity<CommentResponseDto> {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.createComment(createCommentRequestDto, articleId))
    }

    @Operation(summary = "comment 수정")
    @PutMapping("/{commentId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEMBER')")
    fun updateComment(
            @PathVariable articleId: Long,
            @PathVariable commentId: Long,
            @RequestBody updateCommentRequestDto: UpdateCommentRequestDto
    ): ResponseEntity<CommentResponseDto> {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.updateComment(updateCommentRequestDto))
    }

    @Operation(summary = "comment 삭제")
    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEMBER')")
    fun deleteComment(
            @PathVariable articleId: Long,
            @PathVariable commentId: Long,
            @RequestBody deleteCommentRequestDto: DeleteCommentRequestDto
    ): ResponseEntity<Any> {

        commentService.deleteComment(deleteCommentRequestDto)

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("comment 가 삭제되었습니다.")
    }


}