package com.togetor_renewal.togetor.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private LocalDateTime regdate;
    private LocalDateTime updated_at;
    private Long userId;
    private String userNickname;
    private String categoryName;
    private String image;


    public Post(String title, String content, LocalDateTime regdate, LocalDateTime updated_at, Long userId, String userNickname, String categoryName, String image, String siDo, String siGunGu, String eupMyeonDong) {
        this.title = title;
        this.content = content;
        this.regdate = regdate;
        this.updated_at = updated_at;
        this.userId = userId;
        this.userNickname = userNickname;
        this.categoryName = categoryName;
        this.image = image;
    }


}
