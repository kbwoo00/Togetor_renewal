package com.togetor_renewal.togetor.domain.validation.post;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostWriteForm {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private String categoryName;
    private String image;
    @NotBlank
    private String siDo;
    @NotBlank
    private String siGunGu;
    private String eupMyeonDong;
}
