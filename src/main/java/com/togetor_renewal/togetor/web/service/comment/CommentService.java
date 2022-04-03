package com.togetor_renewal.togetor.web.service.comment;

import com.togetor_renewal.togetor.domain.DTO.CommentDTO;
import com.togetor_renewal.togetor.domain.entity.Comment;
import com.togetor_renewal.togetor.domain.entity.Post;
import com.togetor_renewal.togetor.domain.entity.User;
import com.togetor_renewal.togetor.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<CommentDTO> commentList(Long postId) {
        List<Comment> commentList = commentRepository.findCommentsByPost(postId);
        List<CommentDTO> commentDTOList = new ArrayList<>();

        for (Comment comment : commentList) {
            CommentDTO commentDTO = new CommentDTO(
                    comment.getContent(),
                    comment.getUser().getNickname(),
                    comment.getRegdate(),
                    comment.getChgdate(),
                    comment.getCommSeq(),
                    comment.getRecommSeq(),
                    comment.getUser()
            );

            commentDTOList.add(commentDTO);
        }

        return commentDTOList;
    }

    public void commentWrite(Post post, User user, String content) {
        // 가장 최신 댓글 찾기
        Optional<Comment> recentComment = commentRepository.findFirstByPostOrderByCommSeqDesc(post);
        Comment comment;
        // 최신 댓글이 없으면 comm_seq는 1. 아니라면 최근 댓글 comm_seq에 더하기 1
        if (recentComment.isEmpty()) {
            comment = new Comment(
                    content,
                    1,
                    1,
                    post,
                    user
            );
        } else {
            comment = new Comment(
                    content,
                    recentComment.get().getCommSeq() + 1,
                    1,
                    post,
                    user
            );
        }

        commentRepository.save(comment);
    }


    public void recommentWrite(Post post, User user, String content, int commSeq) {

        // 최신 대댓글의 recommSeq에 1더해서 저장
        Optional<Comment> recentRecomm = commentRepository.findFirstByPostAndCommSeqAndRecommSeqNotOrderByRecommSeqDesc(
                post,
                commSeq,
                1
        );

        Comment comment;

        // 대댓글 있으면 그 대댓글 순서에 1하기 없으면 2로 주기(대댓글의 주인 댓글은 1이기 때문)
        if (recentRecomm.isEmpty()) {
            comment = new Comment(
                    content,
                    commSeq,
                    2,
                    post,
                    user
            );
        } else {
            comment = new Comment(
                    content,
                    commSeq,
                    recentRecomm.get().getRecommSeq() + 1,
                    post,
                    user
            );
        }

        commentRepository.save(comment);
    }

    public void delete(Long postId, int commSeq, int recommSeq) {
        commentRepository.updateComment("삭제된 댓글입니다.", postId, commSeq, recommSeq);
    }

    public void modify(Long postId, int commSeq, int recommSeq, String content) {
        commentRepository.updateComment(content, postId, commSeq, recommSeq);
    }
}
