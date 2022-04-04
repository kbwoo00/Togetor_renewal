package com.togetor_renewal.togetor.domain.DTO.post;

import com.togetor_renewal.togetor.domain.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentDTO {
    private String content;
    private String writer;
    private LocalDateTime regdate;
    private LocalDateTime chgdate;
    private int commSeq;
    private int recommSeq;
    private User user;

    public CommentDTO(String content, String writer, LocalDateTime regdate, LocalDateTime chgdate, int commSeq, int recommSeq, User user) {
        this.content = content;
        this.writer = writer;
        this.regdate = regdate;
        this.chgdate = chgdate;
        this.commSeq = commSeq;
        this.recommSeq = recommSeq;
        this.user = user;
    }
}
