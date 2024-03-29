package com.teamsparta.bunnies.domain.post.controller

import com.teamsparta.bunnies.domain.post.dto.request.CreatePostDto
import com.teamsparta.bunnies.domain.post.dto.request.UpdatePostDto
import com.teamsparta.bunnies.domain.post.dto.response.PostDetailResponseDto
import com.teamsparta.bunnies.domain.post.dto.response.PostResponseDto
import com.teamsparta.bunnies.domain.post.service.PostService
import com.teamsparta.bunnies.infra.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
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

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "게시글 작성", description = "게시글을 작성합니다.")
    @PostMapping
    fun createPost(
        @Valid @RequestBody createPostDto: CreatePostDto,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ):ResponseEntity<PostResponseDto>{
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postService.createPost(createPostDto, userPrincipal))
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다.")
    @PutMapping("/{postId}")
    fun updatePost(
        @PathVariable postId: Long,
        @Valid @RequestBody updatePostDto: UpdatePostDto,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ):ResponseEntity<PostResponseDto>{
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postService.updatePost(postId, updatePostDto, userPrincipal))
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "판매된 게시글 상태 변경", description = "게시글 상태를 변경합니다.")
    @PatchMapping("/{postId}")
    fun updateStatus(
        @PathVariable postId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<PostResponseDto>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.updateStatus(postId, userPrincipal))
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    @DeleteMapping("/{postId}")
    fun deletePost(
        @PathVariable postId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<Unit>{
        postService.deletePost(postId, userPrincipal)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "관심 목록 등록", description = "게시글을 관심 목록에 등록합니다.")
    @PostMapping("/{postId}/likes")
    fun addLikes(
        @PathVariable postId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<String>{
        postService.addLikes(postId, userPrincipal)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("관심 목록에 등록하였습니다.")
    }

    @Operation(summary = "관심 목록 개수 조회", description = "관심 목록에 추가된 게시글의 개수를 조회합니다.")
    @GetMapping("/{postId}/likes")
    fun getLikes(
        @PathVariable postId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<Int>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getLikes(postId, userPrincipal))
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "관심 목록 해제", description = "게시글을 관심 목록에서 제외합니다.")
    @DeleteMapping("/{postId}/likes")
    fun deleteLikes(
        @PathVariable postId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<String>{
        postService.deleteLikes(postId, userPrincipal)

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body("관심 목록에서 제외하였습니다.")
    }
}