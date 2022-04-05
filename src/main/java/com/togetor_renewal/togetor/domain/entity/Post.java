package com.togetor_renewal.togetor.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Builder
@Getter @Table(name = "POSTS")
public class Post extends BaseTimeEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;
    private String title;
    private String content;
    private String categoryTitle;
    private String image;
    private String siDo;
    private String siGunGu;
    private String eupMyeonDong;
    @Column(columnDefinition = "long default 0")
    private Long view;
    @Column(name = "WRITER_NICKNAME")
    private String writer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    public Post(String title, String content, String writer, String image, String categoryTitle, String siDo, String siGunGu, String eupMyeonDong, User user) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.image = image;
        this.categoryTitle = categoryTitle;
        this.siDo = siDo;
        this.siGunGu = siGunGu;
        this.eupMyeonDong = eupMyeonDong;
        this.user = user;
    }

    public Post(String title, String content, String categoryTitle, String image, String siDo, String siGunGu, String eupMyeonDong) {
        this.title = title;
        this.content = content;
        this.categoryTitle = categoryTitle;
        this.image = image;
        this.siDo = siDo;
        this.siGunGu = siGunGu;
        this.eupMyeonDong = eupMyeonDong;
    }

}
