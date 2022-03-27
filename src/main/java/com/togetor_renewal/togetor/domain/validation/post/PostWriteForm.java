package com.togetor_renewal.togetor.domain.validation.post;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Data
public class PostWriteForm {
    @NotBlank
    @Max(value = 30, message = "30자까지 가능합니다.")
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
