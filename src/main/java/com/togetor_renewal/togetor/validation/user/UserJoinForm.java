package com.togetor_renewal.togetor.validation.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserJoinForm {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}"
            , message = "패스워드는 대문자, 소문자, 특수문자가 적어도 하나씩은 있어야 하며 최소 8자리여야 하며 최대 20자리까지 가능합니다.")
    private String pass1;
    @NotBlank
    private String pass2;
    @NotBlank
    private String name;
    @NotBlank
    private String phone;

    private String zipcode;
    private String address;
    private String detailAddress;
}
