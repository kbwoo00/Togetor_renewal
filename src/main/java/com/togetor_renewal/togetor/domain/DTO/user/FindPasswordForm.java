package com.togetor_renewal.togetor.domain.DTO.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FindPasswordForm {
    private String name;
    private String email;

    public FindPasswordForm(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
