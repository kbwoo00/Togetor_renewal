package com.togetor_renewal.togetor.web.controller.api;

import com.togetor_renewal.togetor.domain.DTO.post.PostCategory;
import com.togetor_renewal.togetor.domain.entity.District;
import com.togetor_renewal.togetor.domain.entity.Post;
import com.togetor_renewal.togetor.domain.repository.DistrictRepository;
import com.togetor_renewal.togetor.web.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class APIController {

    private final DistrictRepository districtRepository;
    private final PostService postService;

    @PostMapping("/district")
    public List<District> getDistrict() {
        List<District> districtList = districtRepository.findAll();
        return districtList;
    }

    @PostMapping("/posts/{categoryTitle}")
    public List<PostCategory> postCategoryList(@PathVariable String categoryTitle, Model model) {
        List<Post> postList = postService.findPostsByCategory(categoryTitle);

        return getPostCategories(postList,model);
    }


    @PostMapping("/posts/{categoryTitle}/{siDo}")
    public List<PostCategory> postCategoryList(@PathVariable String categoryTitle, @PathVariable String siDo, Model model) {
        List<Post> postList = postService.findPostsByCategoryAndSido(categoryTitle, siDo);

        return getPostCategories(postList,model);
    }

    @PostMapping("/posts/{categoryTitle}/{siDo}/{siGunGu}")
    public List<PostCategory> postCategoryList(@PathVariable String categoryTitle,
                                               @PathVariable String siDo,
                                               @PathVariable String siGunGu, Model model) {
        List<Post> postList = postService.findPostsByCategoryAndSidoAndSigunGu(categoryTitle, siDo, siGunGu);
        return getPostCategories(postList,model);
    }

    @PostMapping("/posts/{categoryTitle}/{siDo}/{siGunGu}/{eupMyeonDong}")
    public List<PostCategory> postCategoryList(@PathVariable String categoryTitle,
                                               @PathVariable String siDo,
                                               @PathVariable String siGunGu,
                                               @PathVariable String eupMyeonDong, Model model) {
        List<Post> postList = postService.findPostsByCategoryAndSidoAndSigunGuAndEupmyeondong(categoryTitle, siDo, siGunGu, eupMyeonDong);
        return getPostCategories(postList,model);
    }

    private List<PostCategory> getPostCategories(List<Post> postList, Model model) {
        List<PostCategory> postCategoryList = new ArrayList<>();

        for (Post post : postList) {
            PostCategory postCategory = new PostCategory(
                    post.getId(), post.getTitle(), post.getContent(), post.getRegdate(),
                    post.getCategoryTitle(), post.getSiDo(), post.getSiGunGu(), post.getEupMyeonDong(),
                    post.getWriter()
            );
            postCategoryList.add(postCategory);
        }
        int totalPosts = postList.size();
        model.addAttribute("totalPosts", totalPosts);

        return postCategoryList;
    }

}
