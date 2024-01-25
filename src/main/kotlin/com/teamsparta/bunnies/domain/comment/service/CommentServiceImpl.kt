package com.teamsparta.bunnies.domain.comment.service


import com.teamsparta.bunnies.domain.comment.dto.CommentResponseDto
import com.teamsparta.bunnies.domain.comment.dto.CreateCommentRequestDto
import com.teamsparta.bunnies.domain.comment.dto.UpdateCommentRequestDto
import com.teamsparta.bunnies.domain.comment.model.Comment
import com.teamsparta.bunnies.domain.comment.model.toResponse
import com.teamsparta.bunnies.domain.comment.repository.CommentRepository
import com.teamsparta.bunnies.domain.exception.InvalidCredentialException
import com.teamsparta.bunnies.domain.exception.ModelNotFoundException
import com.teamsparta.bunnies.domain.post.repository.PostRepository
import com.teamsparta.bunnies.infra.security.UserPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class CommentServiceImpl(
    val postRepository: PostRepository,
    val commentRepository: CommentRepository
) : CommentService {

    @Transactional
    override fun createComment(
        request: CreateCommentRequestDto,
        postId: Long,
        userPrincipal: UserPrincipal
    ): CommentResponseDto {

        // 게시글을 찾아오고, 없으면 예외를 던집니다.
             val targetPost = postRepository.findByIdOrNull(postId)
             ?: throw ModelNotFoundException("Post", postId)
        // 댓글을 생성합니다.
        val comment = Comment(
               post = targetPost,
               comment = request.comment,
               userId = userPrincipal.id
         )
        // 댓글을 저장합니다.
           commentRepository.save(comment)
        // 댓글을 응답 형태로 변환하여 반환합니다.

        return comment.toResponse()
  }
    @Transactional
    //// 요청으로부터 받은 댓글의 ID를 사용하여 데이터베이스에서 해당하는 댓글을 찾은 후 수정.
    override fun updateComment(
        postId: Long,
        commentId: Long,
        request: UpdateCommentRequestDto,
        userPrincipal: UserPrincipal
    ): CommentResponseDto {
        val foundComment = commentId.let { commentRepository.findByIdOrNull(it) }
                ?: throw ModelNotFoundException("Comment", commentId)

        //role이 USER이면서 본인이 아닐 경우에 권한 없음 예외처리
        if ((userPrincipal.id != foundComment.userId) && (userPrincipal.authorities.first().toString() == "ROLE_USER"))
            throw InvalidCredentialException("본인의 글이 아니므로 권한이 없습니다.")


        // 찾아온 댓글이 작성자의 것인지 확인합니다.
//        foundComment.checkAuthentication(request.name)
        // 요청에서 받아온 새로운 댓글 내용으로 댓글을 수정합니다.
        foundComment.comment = request.comment
        // 수정된 댓글을 데이터베이스에 저장합니다.
        commentRepository.save(foundComment)

        return foundComment.toResponse()
    }

    @Transactional
    //댓글삭제.id 를 통해 댓글 호출,null일때 예외
    override fun deleteComment(
        commentId:Long
    ) {
        val foundComment = commentId.let {
            commentRepository.findByIdOrNull(it)
        } ?: throw ModelNotFoundException("Comment", commentId)
// 댓글 작성자인지 확인
//        foundComment.checkAuthentication(request.name)
//해당하는 댓글의 ID를 사용하여 데이터베이스에서 해당 댓글을 삭제
        commentRepository.deleteById(commentId)
    }

}