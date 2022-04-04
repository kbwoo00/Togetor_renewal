package com.togetor_renewal.togetor.domain.DTO.post;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostWriteForm {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private String categoryTitle;
    private String image;
    @NotBlank
    private String siDo;
    @NotBlank
    private String siGunGu;
    @NotBlank
    private String eupMyeonDong;
}
