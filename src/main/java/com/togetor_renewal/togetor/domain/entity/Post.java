package com.togetor_renewal.togetor.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private LocalDateTime regdate;
    private LocalDateTime chgdate;
    private Long userId;
    private String uploadFileName;
    private String storedFileName;
    private String categoryTitle;
    private String image;
    private String siDo;
    private String siGunGu;
    private String eupMyeonDong;


}
