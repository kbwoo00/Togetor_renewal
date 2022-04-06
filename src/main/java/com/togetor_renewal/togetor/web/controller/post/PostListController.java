package com.togetor_renewal.togetor.web.controller.post;

import com.togetor_renewal.togetor.domain.entity.Category;
import com.togetor_renewal.togetor.domain.entity.District;
import com.togetor_renewal.togetor.web.service.post.ClassifyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/posts")
public class PostListController {

    private final ClassifyService classifyService;

    @ModelAttribute("categoryList")
    public List<Category> categories() {
        List<Category> categoryList = classifyService.findAllCategory();
        return categoryList;
    }

    @ModelAttribute("sigunguList")
    public List<District> siGunGus() {
        return new ArrayList<>();
    }

    @ModelAttribute("eupmyeondongList")
    public List<District> eupMyeonDongs() {
        return new ArrayList<>();
    }

    /** 조회 */
    @GetMapping("/{categoryTitle}")
    public String postList(@PathVariable String categoryTitle, Model model) {

        model.addAttribute("categoryTitle", categoryTitle);
        return "template/post/post_list";
    }

    @GetMapping("/{categoryTitle}/{siDo}")
    public String postListSido(@PathVariable String categoryTitle, @PathVariable String siDo, Model model) {

        model.addAttribute("categoryTitle", categoryTitle);

        List<District> sigunguList = classifyService.findAllSigunguBySido(siDo);
        model.addAttribute("sigunguList", sigunguList);

        model.addAttribute("sido", siDo);

        return "template/post/post_list";
    }

    @GetMapping("/{categoryTitle}/{siDo}/{siGunGu}")
    public String postListSigungu(@PathVariable String categoryTitle,
                                  @PathVariable String siDo,
                                  @PathVariable String siGunGu,
                                  Model model
    ) {
        model.addAttribute("categoryTitle", categoryTitle);

        List<District> sigunguList = classifyService.findAllSigunguBySido(siDo);
        model.addAttribute("sigunguList", sigunguList);
        List<District> eupmyeondongList = classifyService.findAllEupmyeondongBySidoAndSigungu(siDo, siGunGu);
        model.addAttribute("eupmyeondongList", eupmyeondongList);

        model.addAttribute("sido", siDo);
        model.addAttribute("sigungu", siGunGu);

        return "template/post/post_list";
    }

    @GetMapping("/{categoryTitle}/{siDo}/{siGunGu}/{eupMyeonDong}")
    public String postListEupmyeondong(@PathVariable String categoryTitle,
                                       @PathVariable String siDo,
                                       @PathVariable String siGunGu,
                                       @PathVariable String eupMyeonDong,
                                       Model model
    ) {

        model.addAttribute("categoryTitle", categoryTitle);

        List<District> sigunguList = classifyService.findAllSigunguBySido(siDo);
        model.addAttribute("sigunguList", sigunguList);
        List<District> eupmyeondongList = classifyService.findAllEupmyeondongBySidoAndSigungu(siDo, siGunGu);
        model.addAttribute("eupmyeondongList", eupmyeondongList);

        model.addAttribute("sido", siDo);
        model.addAttribute("sigungu", siGunGu);
        model.addAttribute("eupmyeondong", eupMyeonDong);

        return "template/post/post_list";
    }
    /** 조회 */
}
