package com.togetor_renewal.togetor.domain.repository;

import com.togetor_renewal.togetor.domain.entity.Comment;
import com.togetor_renewal.togetor.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c join fetch c.post p where p.id= :postId order by c.commSeq, c.recommSeq")
    List<Comment> findCommentsByPost(@Param("postId") Long postId);
    Optional<Comment> findCommentByPostAndCommSeqAndRecommSeq(Post post, int commSeq, int recommSeq);
    Optional<Comment> findFirstByPostOrderByCommSeqDesc(Post post);

    Optional<Comment> findFirstByPostAndCommSeqAndRecommSeqNotOrderByRecommSeqDesc(Post post, int commSeq, int recommSeq);

    @Modifying
    @Transactional
    @Query("update Comment c set c.content= :content where c.post.id= :postId and c.commSeq= :commSeq and c.recommSeq= :recommSeq")
    void updateComment(@Param("content") String content,
                       @Param("postId") Long postId,
                       @Param("commSeq") int commSeq,
                       @Param("recommSeq") int recommSeq);
}
