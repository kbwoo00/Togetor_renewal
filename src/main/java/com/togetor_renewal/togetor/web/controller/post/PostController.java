package com.togetor_renewal.togetor.web.controller.post;

import com.togetor_renewal.togetor.domain.entity.Category;
import com.togetor_renewal.togetor.domain.entity.Post;
import com.togetor_renewal.togetor.domain.repository.CategoryRepository;
import com.togetor_renewal.togetor.domain.repository.PostRepository;
import com.togetor_renewal.togetor.domain.repository.UserRepository;
import com.togetor_renewal.togetor.domain.validation.post.PostWriteForm;
import com.togetor_renewal.togetor.web.Const;
import com.togetor_renewal.togetor.web.service.post.PostService;
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
import java.util.Optional;

@Controller
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @ModelAttribute("categoryList")
    public List<Category> categories(){
        List<Category> categoryList = postService.findAllCategory();
        return categoryList;
    }

    @GetMapping("/posts/{categoryTitle}/{postId}")
    public String post(@PathVariable String categoryTitle, @PathVariable String postId,  Model model){
        Optional<Post> post = postService.findPostByPostId(Long.parseLong(postId));
        // 해당 게시글 없을때 예외 페이지로 던지기
        if (post.isEmpty()){
            return "template/post/post-error";
        }

        model.addAttribute("post", post.get());

        return "template/post/postContent";
    }

    @GetMapping("/posts/{categoryTitle}")
    public String postList(@PathVariable String categoryTitle, Model model){
        List<Post> postList = postService.findPostByCategory(categoryTitle);
        model.addAttribute("postList", postList);
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
                            BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()){
            log.info("err= {}", bindingResult);
            return "template/post/postWrite";
        }

        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute(Const.SESSION_USER_ID);

        postService.write(form, userId, file);

        return "template/post/write-success";
    }

}
