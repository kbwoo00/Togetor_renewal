package com.togetor_renewal.togetor.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter @NoArgsConstructor
@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime regdate;
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

    public User(LocalDateTime regdate, String email, String pass, String passConfirm, String name, String nickname, String phone, String postcode, String address, String detailAddress, String extraAddress) {
        this.regdate = regdate;
        this.email = email;
        this.pass = pass;
        this.passConfirm = passConfirm;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.postcode = postcode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.extraAddress = extraAddress;
    }

    public User(String email, String pass){
        this.email = email;
        this.pass = pass;
    }
}
