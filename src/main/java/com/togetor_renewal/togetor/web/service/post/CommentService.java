package com.togetor_renewal.togetor.web.service.post;

import com.togetor_renewal.togetor.domain.entity.Comment;
import com.togetor_renewal.togetor.domain.entity.Post;
import com.togetor_renewal.togetor.domain.entity.User;
import com.togetor_renewal.togetor.domain.repository.CommentRepository;
import com.togetor_renewal.togetor.web.Const;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

//    public List<Comment> commentList(Post post){
//        return commentRepository.findComments(post);
//    }

    public void write(Post post, User user, String content) {
        // 가장 최신 댓글 찾기
        Optional<Comment> recentComment = commentRepository.findFirstByPostOrderByCommSeqDesc(post);
        Comment comment;
        // 최신 댓글이 없으면 comm_seq는 1. 아니라면 최근 댓글 comm_seq에 더하기 1
        if (recentComment.isEmpty()){
            comment = new Comment(
                    content,
                    1,
                    1,
                    Const.now,
                    post,
                    user
            );
        } else{
            comment = new Comment(
                    content,
                    recentComment.get().getCommSeq() + 1,
                    1,
                    Const.now,
                    post,
                    user
            );
        }

        commentRepository.save(comment);
    }
}
