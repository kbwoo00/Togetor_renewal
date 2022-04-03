package com.togetor_renewal.togetor.web.controller.post;

import com.togetor_renewal.togetor.domain.DTO.post.CommentDTO;
import com.togetor_renewal.togetor.domain.entity.Category;
import com.togetor_renewal.togetor.domain.entity.District;
import com.togetor_renewal.togetor.domain.entity.Post;
import com.togetor_renewal.togetor.domain.DTO.post.PostWriteForm;
import com.togetor_renewal.togetor.web.Const;
import com.togetor_renewal.togetor.web.service.post.CommentService;
import com.togetor_renewal.togetor.web.service.post.PostService;
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
import java.util.*;

@Controller
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    @ModelAttribute("categoryList")
    public List<Category> categories() {
        List<Category> categoryList = postService.findAllCategory();
        return categoryList;
    }

    @ModelAttribute("sigunguList")
    public List<District> siGunGus() {
        return new ArrayList<District>();
    }

    @ModelAttribute("eupmyeondongList")
    public List<District> eupMyeonDongs() {
        return new ArrayList<District>();
    }

    @GetMapping("/post/{postId}")
    public String post(@PathVariable String postId, Model model, HttpServletRequest request) {
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
            } else {
                model.addAttribute("writer", false);
            }
        } catch (NullPointerException e) {
            model.addAttribute("writer", false);
        }

        List<CommentDTO> commentList = commentService.commentList(post.get().getId());
        model.addAttribute("commentList", commentList);

        return "template/post/postContent";
    }
    @PostMapping("/post/{postId}/comment")
    public String commentWrite(@PathVariable String postId, Model model, @RequestParam String content){
        Optional<Post> post = postService.findPostByPostId(Long.parseLong(postId));
        if (post.isEmpty()){
            return "template/post/post-error";
        }
        commentService.commentWrite(post.get(), post.get().getUser(), content);

        model.addAttribute("postId", postId);

        return "template/post/comment-write";
    }
    @PostMapping("/post/{postId}/recomment")
    public String recommentWrite(@PathVariable String postId, Model model,
                                 @RequestParam("content") String content,
                                 @RequestParam("commSeq") int commSeq){
        Optional<Post> post = postService.findPostByPostId(Long.parseLong(postId));
        if (post.isEmpty()){
            return "template/post/post-error";
        }
        commentService.recommentWrite(post.get(), post.get().getUser(), content, commSeq);

        model.addAttribute("postId", postId);

        return "template/post/comment-write";
    }
    @PostMapping("/post/{postId}/comment/delete/{commSeq}/{recommSeq}")
    public void commentDelete(
            @PathVariable String postId,
            @PathVariable String commSeq,
            @PathVariable String recommSeq){
        commentService.delete(Long.parseLong(postId), Integer.parseInt(commSeq), Integer.parseInt(recommSeq));
    }
    @PostMapping("/post/{postId}/comment/modify/{commSeq}/{recommSeq}")
    public String commentModify(
            @PathVariable String postId,
            @PathVariable String commSeq,
            @PathVariable String recommSeq,
            @RequestParam String content,
            Model model){
        commentService.modify(Long.parseLong(postId), Integer.parseInt(commSeq), Integer.parseInt(recommSeq), content);
        model.addAttribute("postId", postId);
        return "template/post/comment-write";
    }

    @GetMapping("/posts/{categoryTitle}")
    public String postList(@PathVariable String categoryTitle, Model model) {

        model.addAttribute("categoryTitle", categoryTitle);
        return "template/post/postList";
    }

    @GetMapping("/posts/{categoryTitle}/{siDo}")
    public String postListSido(@PathVariable String categoryTitle, @PathVariable String siDo, Model model) {

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
                                  Model model
    ) {
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
                                       Model model
                                       ) {

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

        postService.modify(form, postId, file);

        model.addAttribute("postId", postId);
        return "template/post/modify-success";
    }

    @PostMapping("/post/delete/{postId}")
    public void postDelete(@PathVariable String postId) {
        postService.delete(Long.parseLong(postId));
    }

}
