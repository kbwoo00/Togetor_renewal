package com.togetor_renewal.togetor.web.service.post;

import com.togetor_renewal.togetor.domain.entity.Category;
import com.togetor_renewal.togetor.domain.entity.District;
import com.togetor_renewal.togetor.domain.entity.Post;
import com.togetor_renewal.togetor.domain.entity.User;
import com.togetor_renewal.togetor.domain.repository.CategoryRepository;
import com.togetor_renewal.togetor.domain.repository.DistrictRepository;
import com.togetor_renewal.togetor.domain.repository.PostRepository;
import com.togetor_renewal.togetor.domain.repository.UserRepository;
import com.togetor_renewal.togetor.domain.DTO.post.PostWriteForm;
import com.togetor_renewal.togetor.web.Const;
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
    private final DistrictRepository districtRepository;
    private final CategoryRepository categoryRepository;
    private final S3FileUploadService fileUploadService;
    private final UserRepository userRepository;

    public List<Category> findAllCategory(){
        return categoryRepository.findAll();
    }

    public List<Post> findFivePostsByCategory(String categoryTitle){
        return postRepository.findFirst5ByCategoryTitleOrderByIdDesc(categoryTitle);
    }

    public Optional<Post> findPostByPostId(Long postId) {
        return postRepository.findById(postId);
    }

    public void write(PostWriteForm form, Long userId, MultipartFile file) throws IOException {

        User user = userRepository.getById(userId);
        Post post = new Post(
                form.getTitle(),
                form.getContent(),
                Const.now,
                null,
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

    public List<District> findAllSigunguBySido(String siDo) {
        return districtRepository.findAllBySiDoAndSiGunGuIsNotNullAndEupMyeonDongIsNull(siDo);
    }

    public List<District> findAllEupmyeondongBySidoAndSigungu(String siDo, String siGunGu){
        return districtRepository.findAllBySiDoAndSiGunGuAndEupMyeonDongIsNotNull(siDo, siGunGu);
    }

    public List<Post> findPostsByCategory(String categoryTitle){
        return postRepository.findPostsByCategoryTitleOrderByIdDesc(categoryTitle);
    }

    public List<Post> findPostsByCategoryAndSido(String categoryTitle, String siDo){
        return postRepository.findPostsByCategoryTitleAndSiDoOrderByIdDesc(categoryTitle, siDo);
    }
    public List<Post> findPostsByCategoryAndSidoAndSigunGu(String categoryTitle, String siDo, String siGunGu){
        return postRepository.findPostsByCategoryTitleAndSiDoAndSiGunGuOrderByIdDesc(categoryTitle, siDo, siGunGu);
    }
    public List<Post> findPostsByCategoryAndSidoAndSigunGuAndEupmyeondong(String categoryTitle, String siDo, String siGunGu, String eupMyeonDong){
        return postRepository.findPostsByCategoryTitleAndSiDoAndSiGunGuAndEupMyeonDongOrderByIdDesc(categoryTitle, siDo, siGunGu, eupMyeonDong);
    }


}
