package com.teamsparta.bunnies.domain.image.controller

import com.teamsparta.bunnies.domain.image.dto.UploadImageResponse
import com.teamsparta.bunnies.infra.aws.S3Service
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Tag(name = "image", description = "이미지 관리 API")
@RequestMapping("/api/images")
@RestController
class ImageController(
    private val s3Service: S3Service
) {
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun uploadImage(@RequestParam("image") multipartFile: MultipartFile): ResponseEntity<UploadImageResponse> {
        return ResponseEntity
            .ok(UploadImageResponse(url=s3Service.upload(multipartFile)))

    }
}