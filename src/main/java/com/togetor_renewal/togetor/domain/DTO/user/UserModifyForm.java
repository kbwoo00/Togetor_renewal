package com.togetor_renewal.togetor.domain.DTO.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserModifyForm {
    private Long userId;
    @NotBlank
    @Email
    private String email;
    @Email
    private String newEmail;
    private String pass;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$")
    private String newPass;
    private String newPassConfirm;
    @NotBlank
    private String name;
    @NotBlank
    @Size(min = 3, max = 20, message = "별명은 3 ~ 20자까지 가능합니다.")
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
    private boolean isSuccess;
}
