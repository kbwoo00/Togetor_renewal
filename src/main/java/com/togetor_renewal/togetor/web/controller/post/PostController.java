package com.togetor_renewal.togetor.web.controller.post;

import com.togetor_renewal.togetor.domain.entity.Category;
import com.togetor_renewal.togetor.domain.entity.Post;
import com.togetor_renewal.togetor.domain.repository.CategoryRepository;
import com.togetor_renewal.togetor.domain.repository.PostRepository;
import com.togetor_renewal.togetor.domain.repository.UserRepository;
import com.togetor_renewal.togetor.domain.validation.post.PostWriteForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    @ModelAttribute("categoryList")
    public List<Category> categories(){
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList;
    }

    @GetMapping("/posts")
    public String postList(){
        return "template/post/postList";
    }

    @GetMapping("/post/write")
    public String postWriteForm(Model model){
        model.addAttribute("post", new Post());
        return "template/post/postWrite";
    }

    @PostMapping("/post/write")
    public String postWrite(@Validated @ModelAttribute("post") PostWriteForm form,
                            BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()){
            log.info("err= {}", bindingResult);
            return "template/post/postWrite";
        }

        /**
         * TODO
         * 검증처리(HTML)에 에러메시지 띄울수 있도록하기
         * 검증처리된 폼을 Post에 담아서 DB에 저장하기
         */
        Post post = new Post();


        return "redirect:/";
    }

}
