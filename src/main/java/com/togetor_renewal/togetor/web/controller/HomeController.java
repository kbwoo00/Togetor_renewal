package com.togetor_renewal.togetor.web.controller;

import com.togetor_renewal.togetor.domain.entity.Category;
import com.togetor_renewal.togetor.domain.entity.Post;
import com.togetor_renewal.togetor.web.service.post.ClassifyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ClassifyService classifyService;

    @GetMapping("/")
    public String home(Model model)
    {
        List<Category> categoryList = classifyService.findAllCategory();
        model.addAttribute(categoryList);
        List<Post> postList = new ArrayList<>();
        for (Category category : categoryList) {
            postList.addAll(classifyService.findFivePostsByCategory(category.getTitle()));
        }
        model.addAttribute(postList);

        return "home";
    }

}
