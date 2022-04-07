package com.togetor_renewal.togetor.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter @NoArgsConstructor
@Builder
@Entity @Table(name = "USERS")
public class User extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;
    private String email;
    private String pass;
    @Transient
    private String passConfirm;
    private String name;
    private String nickname;
    private String phone;
    private String postcode;
    private String address;
    private String detailAddress;
    private String extraAddress;
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Post> posts = new ArrayList<>();
    // 댓글 목록 유저가 확인하고 싶으면 필요(양방향)
//    @OneToMany(mappedBy = "user")
//    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Bookmark> bookmarks = new ArrayList<>();

    public User(String email, String pass, String name, String nickname, String phone, String postcode, String address, String detailAddress, String extraAddress) {
        this.email = email;
        this.pass = pass;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.postcode = postcode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.extraAddress = extraAddress;
    }
}
