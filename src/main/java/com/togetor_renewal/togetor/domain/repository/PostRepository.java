package com.togetor_renewal.togetor.domain.repository;

import com.togetor_renewal.togetor.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findById(Long postId);
    List<Post> findFirst5ByCategoryTitleOrderByIdDesc(String categoryTitle);
    @Query("select p,c from Post p left join p.comments c where p.categoryTitle= :categoryTitle")
    List<Post> findPostsByCategoryTitleOrderByIdDesc(@Param("categoryTitle") String categoryTitle);
    List<Post> findPostsByCategoryTitleAndSiDoOrderByIdDesc(String categoryTitle, String siDo);
    List<Post> findPostsByCategoryTitleAndSiDoAndSiGunGuOrderByIdDesc(String categoryTitle, String siDo, String siGunGu);
    List<Post> findPostsByCategoryTitleAndSiDoAndSiGunGuAndEupMyeonDongOrderByIdDesc(String categoryTitle, String siDo, String siGunGu, String eupMyeonDong);

    @Transactional
    @Modifying
    @Query("update Post p set p.title= :title, p.content= :content, p.categoryTitle= :categoryTitle, p.chgdate= :chgdate, p.image= :image, p.siDo= :siDo, p.siGunGu= :siGunGu, p.eupMyeonDong= :eupMyeonDong where p.id= :postId")
    void updatePost(@Param("title") String title, @Param("content") String content, @Param("categoryTitle") String categoryTitle , @Param("chgdate")LocalDateTime chgdate, @Param("image") String image, @Param("siDo") String siDo, @Param("siGunGu") String siGunGu, @Param("eupMyeonDong") String eupMyeonDong, @Param("postId") Long postId);


}
