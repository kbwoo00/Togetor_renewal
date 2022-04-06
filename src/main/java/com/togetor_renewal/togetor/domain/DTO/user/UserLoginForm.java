package com.togetor_renewal.togetor.domain.DTO.user;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter @Setter
public class UserLoginForm {
    @NotBlank
    private String email;
    @NotBlank
    private String pass;
}
