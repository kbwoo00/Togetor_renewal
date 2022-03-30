package com.togetor_renewal.togetor.web.controller;

import com.togetor_renewal.togetor.domain.entity.Category;
import com.togetor_renewal.togetor.domain.entity.District;
import com.togetor_renewal.togetor.domain.repository.CategoryRepository;
import com.togetor_renewal.togetor.domain.repository.PostRepository;
import com.togetor_renewal.togetor.web.Const;
import com.togetor_renewal.togetor.web.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    @ModelAttribute("sigunguList")
    public List<District> siGunGus(){
        return new ArrayList<District>();
    }
    @ModelAttribute("eupmyeondongList")
    public List<District> eupMyeonDongs(){
        return new ArrayList<District>();
    }

    @GetMapping("/")
    public String home(Model model)
    {
        List<Category> categories = postService.findAllCategory();
        model.addAttribute("categories", categories);

        return "home";
    }


}
