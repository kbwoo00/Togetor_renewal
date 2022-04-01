package com.togetor_renewal.togetor.domain.repository;

import com.togetor_renewal.togetor.domain.entity.Comment;
import com.togetor_renewal.togetor.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment, Long> {

//    @Query("select c from Comment c join fetch c.post order by c.commSeq, c.recommSeq")
//    List<Comment> findComments(Post post);

    Optional<Comment> findFirstByPostOrderByCommSeqDesc(Post post);

}
