package com.togetor_renewal.togetor.web.service.post;

import com.togetor_renewal.togetor.domain.entity.Post;
import com.togetor_renewal.togetor.domain.entity.User;
import com.togetor_renewal.togetor.domain.repository.PostRepository;
import com.togetor_renewal.togetor.domain.repository.UserRepository;
import com.togetor_renewal.togetor.domain.DTO.post.PostWriteForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final S3FileUploadService fileUploadService;
    private final UserRepository userRepository;

    public Optional<Post> findPostByPostId(Long postId) {
        return postRepository.findById(postId);
    }

    public void write(PostWriteForm form, Long userId, MultipartFile file) throws IOException {

        User user = userRepository.getById(userId);
        Post post = new Post(
                form.getTitle(),
                form.getContent(),
                user.getNickname(),
                fileUploadService.upload(file),
                form.getCategoryTitle(),
                form.getSiDo(),
                form.getSiGunGu(),
                form.getEupMyeonDong(),
                user
        );

        postRepository.save(post);

    }

    public void modify(PostWriteForm form, String postId, MultipartFile file) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime chgdate = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), now.getMinute());
        postRepository.updatePost(
                form.getTitle(),
                form.getContent(),
                form.getCategoryTitle(),
                chgdate,
                fileUploadService.upload(file),
                form.getSiDo(),
                form.getSiGunGu(),
                form.getEupMyeonDong(),
                Long.parseLong(postId)
        );
    }

    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }

    public void updateView(Long postId) {
        postRepository.updateView(postId);
    }
}
