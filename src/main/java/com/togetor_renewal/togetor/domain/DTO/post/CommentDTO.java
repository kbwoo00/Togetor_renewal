package com.togetor_renewal.togetor.domain.DTO.post;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentDTO {
    private String content;
    private String writer;
    private LocalDateTime regdate;

    public CommentDTO(String content, String writer, LocalDateTime regdate) {
        this.content = content;
        this.writer = writer;
        this.regdate = regdate;
    }
}
