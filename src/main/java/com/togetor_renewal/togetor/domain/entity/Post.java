package com.togetor_renewal.togetor.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter @Table(name = "POSTS")
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;
    private String title;
    private String content;
    private LocalDateTime regdate;
    private LocalDateTime chgdate;
    private String categoryTitle;
    private String image;
    private String siDo;
    private String siGunGu;
    private String eupMyeonDong;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    public Post(String title, String content, LocalDateTime regdate, LocalDateTime chgdate, User user, String image, String categoryTitle, String siDo, String siGunGu, String eupMyeonDong) {
        this.title = title;
        this.content = content;
        this.regdate = regdate;
        this.chgdate = chgdate;
        this.user = user;
        this.image = image;
        this.categoryTitle = categoryTitle;
        this.siDo = siDo;
        this.siGunGu = siGunGu;
        this.eupMyeonDong = eupMyeonDong;
    }
}
