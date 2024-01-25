package com.teamsparta.bunnies.domain.comment.controller

import com.teamsparta.bunnies.domain.comment.dto.CommentResponseDto
import com.teamsparta.bunnies.domain.comment.dto.CreateCommentRequestDto
import com.teamsparta.bunnies.domain.comment.dto.UpdateCommentRequestDto
import com.teamsparta.bunnies.domain.comment.service.CommentService
import com.teamsparta.bunnies.infra.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@Tag(name = "comments", description = "댓글 API")
@RequestMapping("/api/posts/{postId}/comments")
@RestController
class CommentController(
        val commentService: CommentService
) {

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "comment 생성")
    @PostMapping
    fun createComment(
        @PathVariable postId: Long,
        @RequestBody createCommentRequestDto: CreateCommentRequestDto,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<CommentResponseDto> {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.createComment(createCommentRequestDto, postId, userPrincipal))
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "comment 수정")
    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @RequestBody updateCommentRequestDto: UpdateCommentRequestDto,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<CommentResponseDto> {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.updateComment(postId, commentId, updateCommentRequestDto, userPrincipal))
    }


    @Operation(summary = "comment 삭제")
    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<Unit> {

        commentService.deleteComment(commentId, userPrincipal)

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }


}