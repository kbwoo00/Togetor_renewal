package com.togetor_renewal.togetor.web.controller.post;

import com.togetor_renewal.togetor.domain.entity.Category;
import com.togetor_renewal.togetor.domain.entity.Post;
import com.togetor_renewal.togetor.domain.repository.CategoryRepository;
import com.togetor_renewal.togetor.domain.repository.PostRepository;
import com.togetor_renewal.togetor.domain.repository.UserRepository;
import com.togetor_renewal.togetor.domain.validation.post.PostWriteForm;
import com.togetor_renewal.togetor.web.Const;
import com.togetor_renewal.togetor.web.service.post.S3FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;
    private final S3FileUploadService fileUploadService;
    private final CategoryRepository categoryRepository;

    @ModelAttribute("categoryList")
    public List<Category> categories(){
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList;
    }

    @GetMapping("/posts/{categoryTitle}")
    public String postList(){
        /**
         * TODO
         * 카테고리
         */
        return "template/post/postList";
    }

    @GetMapping("/post/write")
    public String postWriteForm(Model model){
        model.addAttribute("post", new Post());
        return "template/post/postWrite";
    }

    @PostMapping("/post/write")
    public String postWrite(@Validated @ModelAttribute("post") PostWriteForm form,
                            HttpServletRequest request,
                            @RequestParam MultipartFile file,
                            BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()){
            log.info("err= {}", bindingResult);
            return "template/post/postWrite";
        }

        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute(Const.SESSION_USER_ID);

        LocalDateTime now = LocalDateTime.now();
        Post post = new Post(
                form.getTitle(),
                form.getContent(),
                LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(),now.getHour(), now.getMinute(), now.getSecond()),
                null,
                userId,
                fileUploadService.upload(file),
                form.getCategoryTitle(),
                form.getSiDo(),
                form.getSiGunGu(),
                form.getEupMyeonDong()
                );

        postRepository.save(post);

        return "redirect:/";
    }

}
