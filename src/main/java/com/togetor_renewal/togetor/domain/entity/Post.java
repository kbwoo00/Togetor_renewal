package com.togetor_renewal.togetor.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "WRITER_NICKNAME")
    private String writer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    public Post(String title, String content, LocalDateTime regdate, LocalDateTime chgdate, String writer, String image, String categoryTitle, String siDo, String siGunGu, String eupMyeonDong, User user) {
        this.title = title;
        this.content = content;
        this.regdate = regdate;
        this.chgdate = chgdate;
        this.writer = writer;
        this.image = image;
        this.categoryTitle = categoryTitle;
        this.siDo = siDo;
        this.siGunGu = siGunGu;
        this.eupMyeonDong = eupMyeonDong;
        this.user = user;
    }

    public Post(String title, String content, LocalDateTime chgdate, String categoryTitle, String image, String siDo, String siGunGu, String eupMyeonDong) {
        this.title = title;
        this.content = content;
        this.chgdate = chgdate;
        this.categoryTitle = categoryTitle;
        this.image = image;
        this.siDo = siDo;
        this.siGunGu = siGunGu;
        this.eupMyeonDong = eupMyeonDong;
    }

}
