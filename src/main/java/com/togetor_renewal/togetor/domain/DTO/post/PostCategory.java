package com.togetor_renewal.togetor.domain.DTO.post;

import com.togetor_renewal.togetor.domain.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostCategory {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime regdate;
    private String categoryTitle;
    private String siDo;
    private String siGunGu;
    private String eupMyeonDong;
    private String nickname;
    private Long view;
    private Long commentCount;

    public PostCategory(Long id, String title, String content, LocalDateTime regdate, String categoryTitle, String siDo, String siGunGu, String eupMyeonDong, String nickname, Long view, Long commentCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.regdate = regdate;
        this.categoryTitle = categoryTitle;
        this.siDo = siDo;
        this.siGunGu = siGunGu;
        this.eupMyeonDong = eupMyeonDong;
        this.nickname = nickname;
        this.view = view;
        this.commentCount = commentCount;
    }
}
