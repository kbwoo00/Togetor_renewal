package com.togetor_renewal.togetor.web.service.post;

import com.togetor_renewal.togetor.domain.entity.Category;
import com.togetor_renewal.togetor.domain.entity.Post;
import com.togetor_renewal.togetor.domain.entity.User;
import com.togetor_renewal.togetor.domain.repository.CategoryRepository;
import com.togetor_renewal.togetor.domain.repository.PostRepository;
import com.togetor_renewal.togetor.domain.repository.UserRepository;
import com.togetor_renewal.togetor.domain.validation.post.PostWriteForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final S3FileUploadService fileUploadService;
    private final UserRepository userRepository;

    public void write(PostWriteForm form, Long userId, MultipartFile file) throws IOException {

        LocalDateTime now = LocalDateTime.now();
        User user = userRepository.getById(userId);
        Post post = new Post(
                form.getTitle(),
                form.getContent(),
                LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(),now.getHour(), now.getMinute(), now.getSecond()),
                null,
                user,
                fileUploadService.upload(file),
                form.getCategoryTitle(),
                form.getSiDo(),
                form.getSiGunGu(),
                form.getEupMyeonDong()
        );

        postRepository.save(post);

    }

    public List<Category> findAllCategory(){
        return categoryRepository.findAll();
    }

    public List<Post> findPostByCategory(String categoryTitle){
       return postRepository.findAllByCategoryTitle(categoryTitle);
    }

    public Optional<Post> findPostByPostId(Long postId) {
        return postRepository.findById(postId);
    }
}
