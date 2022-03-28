package com.togetor_renewal.togetor.domain.repository;

import com.togetor_renewal.togetor.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByCategoryTitle(String categoryTitle);
    Optional<Post> findById(Long postId);
}
