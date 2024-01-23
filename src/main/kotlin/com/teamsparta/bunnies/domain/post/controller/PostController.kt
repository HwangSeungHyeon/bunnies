package com.teamsparta.bunnies.domain.post.controller

import com.teamsparta.bunnies.domain.post.dto.request.CreatePostDto
import com.teamsparta.bunnies.domain.post.dto.request.UpdatePostDto
import com.teamsparta.bunnies.domain.post.dto.response.PostDetailResponseDto
import com.teamsparta.bunnies.domain.post.dto.response.PostResponseDto
import com.teamsparta.bunnies.domain.post.service.PostService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "posts", description = "게시글 API")
@RequestMapping("/api/posts")
@RestController
class PostController(
    private val postService: PostService
){
    @Operation(summary = "게시글 목록 조회", description = "게시글 목록을 조회합니다.")
    @GetMapping
    fun getAllPost(
        @RequestParam(name = "page", defaultValue = "0")
        page: Int,

        @RequestParam(name = "size", defaultValue = "10")
        size: Int
    ): ResponseEntity<List<PostResponseDto>>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getAllPost(page, size))
    }

    @Operation(summary = "게시글 상세조회", description = "게시글을 상세조회합니다.")
    @GetMapping("/{postId}")
    fun getPost(
        @PathVariable postId: Long
    ): ResponseEntity<PostDetailResponseDto>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPost(postId))
    }

    @Operation(summary = "게시글 작성", description = "게시글을 작성합니다.")
    @PostMapping
    fun createPost(
        @Valid @RequestBody createPostDto: CreatePostDto
    ):ResponseEntity<PostResponseDto>{
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postService.createPost(createPostDto))
    }

    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다.")
    @PutMapping("/{postId}")
    fun updatePost(
        @PathVariable postId: Long,
        @Valid @RequestBody updatePostDto: UpdatePostDto
    ):ResponseEntity<PostResponseDto>{
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postService.updatePost(postId, updatePostDto))
    }

    @Operation(summary = "판매된 게시글 상태 변경", description = "게시글 상태를 변경합니다.")
    @PatchMapping("/{postId}")
    fun updateStatus(
        @PathVariable postId: Long
    ): ResponseEntity<PostResponseDto>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.updateStatus(postId))
    }

    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    @DeleteMapping("/{postId}")
    fun deletePost(
        @PathVariable postId: Long
    ): ResponseEntity<Unit>{
        postService.deletePost(postId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}