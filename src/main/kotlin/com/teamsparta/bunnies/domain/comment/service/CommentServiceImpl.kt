package com.teamsparta.newsfeed.domain.comment.service

import com.teamsparta.newsfeed.domain.article.repository.ArticleRepository
import com.teamsparta.newsfeed.domain.comment.dto.CommentResponseDto
import com.teamsparta.newsfeed.domain.comment.dto.CreateCommentRequestDto
import com.teamsparta.newsfeed.domain.comment.dto.DeleteCommentRequestDto
import com.teamsparta.newsfeed.domain.comment.dto.UpdateCommentRequestDto
import com.teamsparta.newsfeed.domain.comment.model.Comment
import com.teamsparta.newsfeed.domain.comment.model.toResponse
import com.teamsparta.newsfeed.domain.comment.repository.CommentRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentServiceImpl(
        val articleRepository: ArticleRepository,
        val commentRepository: CommentRepository
) : CommentService {

    @Transactional
    override fun createComment(request: CreateCommentRequestDto, articleId: Long): CommentResponseDto {
        val targetArticle = articleRepository.findByIdOrNull(articleId)
                ?: throw Exception("target article is not found")

        val comment = Comment(
                article = targetArticle,
                comment = request.comment,
                name = request.name
        )
        commentRepository.save(comment)

        return comment.toResponse()
    }

    @Transactional
    override fun updateComment(request: UpdateCommentRequestDto): CommentResponseDto {
        val foundComment = request.id.let { commentRepository.findByIdOrNull(it) }
                ?: throw Exception("target comment is not found")

        foundComment.checkAuthentication(request.name)
        foundComment.comment = request.comment

//        commentRepository.save(foundComment)

        return foundComment.toResponse()
    }

    @Transactional
    override fun deleteComment(request: DeleteCommentRequestDto) {
        val foundComment = request.id.let {
            commentRepository.findByIdOrNull(it)
        } ?: throw Exception("target comment is not found")

        foundComment.checkAuthentication(request.name)

        commentRepository.deleteById(request.id)
    }

}