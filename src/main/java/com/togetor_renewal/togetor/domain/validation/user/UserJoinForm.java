package com.togetor_renewal.togetor.domain.validation.user;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserJoinForm {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$")
    private String pass;
    @NotBlank
    private String passConfirm;
    @NotBlank
    private String name;
    @NotBlank
    @Size(min = 5, max = 20, message = "별명은 5 ~ 20자까지 가능합니다.")
    private String nickname;
    @NotBlank
    @Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$",
    message = "올바른 형식이 아닙니다.")
    private String phone;

    @NotBlank
    private String postcode;
    @NotBlank
    private String address;
    @NotBlank
    private String detailAddress;
    private String extraAddress;
}
