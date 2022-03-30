package com.togetor_renewal.togetor.web.controller.post;

import com.togetor_renewal.togetor.domain.entity.Category;
import com.togetor_renewal.togetor.domain.entity.District;
import com.togetor_renewal.togetor.domain.entity.Post;
import com.togetor_renewal.togetor.domain.entity.User;
import com.togetor_renewal.togetor.domain.repository.CategoryRepository;
import com.togetor_renewal.togetor.domain.repository.PostRepository;
import com.togetor_renewal.togetor.domain.repository.UserRepository;
import com.togetor_renewal.togetor.domain.validation.post.PostWriteForm;
import com.togetor_renewal.togetor.web.Const;
import com.togetor_renewal.togetor.web.service.post.PostService;
import com.togetor_renewal.togetor.web.service.post.S3FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import java.util.*;

@Controller
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @ModelAttribute("categoryList")
    public List<Category> categories() {
        List<Category> categoryList = postService.findAllCategory();
        return categoryList;
    }
    @ModelAttribute("sigunguList")
    public List<District> siGunGus(){
        return new ArrayList<District>();
    }
    @ModelAttribute("eupmyeondongList")
    public List<District> eupMyeonDongs(){
        return new ArrayList<District>();
    }

    @GetMapping("/post/{categoryTitle}/{postId}")
    public String post(@PathVariable String categoryTitle, @PathVariable String postId, Model model, HttpServletRequest request) {
        Optional<Post> post = postService.findPostByPostId(Long.parseLong(postId));
        // 해당 게시글 없을때 예외 페이지로 던지기
        if (post.isEmpty()) {
            return "template/post/post-error";
        }

        model.addAttribute("post", post.get());

        HttpSession session = request.getSession(false);

        // 글 수정 권한 있는지
        try {
            if (session.getAttribute(Const.SESSION_USER_ID).equals(post.get().getUser().getId())) {
                model.addAttribute("writer", true);
            } else{
                model.addAttribute("writer", false);
            }
        } catch (NullPointerException e) {
            model.addAttribute("writer", false);
        }

        return "template/post/postContent";
    }
    public void addPostsModel(Page<Post> postList, Model model){
        model.addAttribute("postList", postList);

        int startPage = Math.max(1, postList.getPageable().getPageNumber() - 3) ;
        int endPage = Math.min(postList.getTotalPages(), postList.getPageable().getPageNumber() + 5);
        log.info("startPage={}", startPage);
        log.info("endPage={}", endPage);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
    }

    @GetMapping("/posts/{categoryTitle}")
    public String postList(@PathVariable String categoryTitle, Model model, @PageableDefault(size = 1) Pageable pageable) {
        Page<Post> postList = postService.findPostByCategoryPage(categoryTitle, pageable);
        addPostsModel(postList, model);

        model.addAttribute("categoryTitle", categoryTitle);
        return "template/post/postList";
    }

    @GetMapping("/posts/{categoryTitle}/{siDo}")
    public String postListSido(@PathVariable String categoryTitle, @PathVariable String siDo, Model model, @PageableDefault(size = 1) Pageable pageable){
        Page<Post> postList = postService.findPostsByCategoryTitleAndSido(categoryTitle, siDo);
        addPostsModel(postList, model);

        model.addAttribute("categoryTitle", categoryTitle);

        List<District> sigunguList = postService.findAllSigunguBySido(siDo);
        model.addAttribute("sigunguList", sigunguList);

        model.addAttribute("sido", siDo);

        return "template/post/postList";
    }

    @GetMapping("/posts/{categoryTitle}/{siDo}/{siGunGu}")
    public String postListSigungu(@PathVariable String categoryTitle,
                                  @PathVariable String siDo,
                                  @PathVariable String siGunGu,
                                  Model model,
                                  @PageableDefault(size = 1) Pageable pageable){
        Page<Post> postList = postService.findPostsByCategoryTitleAndSidoAndSigungu(categoryTitle, siDo, siGunGu);
        addPostsModel(postList, model);
        model.addAttribute("categoryTitle", categoryTitle);

        List<District> sigunguList = postService.findAllSigunguBySido(siDo);
        model.addAttribute("sigunguList", sigunguList);
        List<District> eupmyeondongList = postService.findAllEupmyeondongBySidoAndSigungu(siDo, siGunGu);
        model.addAttribute("eupmyeondongList", eupmyeondongList);

        model.addAttribute("sido", siDo);
        model.addAttribute("sigungu", siGunGu);

        return "template/post/postList";
    }

    @GetMapping("/posts/{categoryTitle}/{siDo}/{siGunGu}/{eupMyeonDong}")
    public String postListEupmyeondong(@PathVariable String categoryTitle,
                                       @PathVariable String siDo,
                                       @PathVariable String siGunGu,
                                       @PathVariable String eupMyeonDong,
                                       Model model,
                                       @PageableDefault(size = 1) Pageable pageable){
        Page<Post> postList = postService.findPostsByCategoryTitleAndSidoAndSigunguAndEupmyeondong(categoryTitle, siDo, siGunGu, eupMyeonDong);
        addPostsModel(postList, model);

        model.addAttribute("categoryTitle", categoryTitle);

        List<District> sigunguList = postService.findAllSigunguBySido(siDo);
        model.addAttribute("sigunguList", sigunguList);
        List<District> eupmyeondongList = postService.findAllEupmyeondongBySidoAndSigungu(siDo, siGunGu);
        model.addAttribute("eupmyeondongList", eupmyeondongList);

        model.addAttribute("sido", siDo);
        model.addAttribute("sigungu", siGunGu);
        model.addAttribute("eupmyeondong", eupMyeonDong);

        return "template/post/postList";
    }

    @GetMapping("/post/write")
    public String postWriteForm(Model model) {
        model.addAttribute("post", new Post());
        return "template/post/postWrite";
    }

    @PostMapping("/post/write")
    public String postWrite(@Validated @ModelAttribute("post") PostWriteForm form,
                            HttpServletRequest request,
                            @RequestParam MultipartFile file,
                            BindingResult bindingResult, Model model) throws IOException {
        //필드 검증
        if (bindingResult.hasErrors()) {
            log.info("err= {}", bindingResult);
            return "template/post/postWrite";
        }

        // 글쓰기
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute(Const.SESSION_USER_ID);

        postService.write(form, userId, file);

        return "template/post/write-success";
    }

    @GetMapping("/post/modify/{postId}")
    public String postModifyForm(@PathVariable String postId, Model model) {

        Optional<Post> post = postService.findPostByPostId(Long.parseLong(postId));

        model.addAttribute("post", post.get());

        return "template/post/postModify";
    }

    @PostMapping("/post/modify/{postId}")
    public String postModify(@PathVariable String postId,
            @Validated @ModelAttribute("post") PostWriteForm form,
                            @RequestParam MultipartFile file,
                            BindingResult bindingResult, Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("err= {}", bindingResult);
            return "template/post/postModify";
        }


        postService.modifyPost(form, postId, file);

        model.addAttribute("postId", postId);
        return "template/post/modify-success";
    }

    @PostMapping("/post/delete/{postId}")
    public void postDelete(@PathVariable String postId){
        postService.delete(Long.parseLong(postId));
    }

}
