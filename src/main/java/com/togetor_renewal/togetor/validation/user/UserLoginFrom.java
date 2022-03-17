package com.togetor_renewal.togetor.validation.user;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class UserLoginFrom {
    @NotBlank
    private String email;
    @NotBlank
    private String pass;

}
