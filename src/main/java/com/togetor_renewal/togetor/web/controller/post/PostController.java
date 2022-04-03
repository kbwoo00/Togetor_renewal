package com.togetor_renewal.togetor.web.controller.post;

import com.togetor_renewal.togetor.domain.DTO.CommentDTO;
import com.togetor_renewal.togetor.domain.entity.Post;
import com.togetor_renewal.togetor.domain.DTO.PostWriteForm;
import com.togetor_renewal.togetor.domain.entity.User;
import com.togetor_renewal.togetor.web.Const;
import com.togetor_renewal.togetor.web.service.comment.CommentService;
import com.togetor_renewal.togetor.web.service.post.ClassifyService;
import com.togetor_renewal.togetor.web.service.post.PostService;
import com.togetor_renewal.togetor.web.service.user.UserService;
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
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final ClassifyService classifyService;
    private final CommentService commentService;

    @GetMapping("/{postId}")
    public String post(@PathVariable String postId, Model model, HttpServletRequest request) {
        Optional<Post> post = postService.findPostByPostId(Long.parseLong(postId));
        // 해당 게시글 없을때 예외 페이지로 던지기
        if (post.isEmpty()) {
            return "template/post/error/post_error";
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

        return "template/post/post_content";
    }

    /** 게시글 Create, Update, Delete */
    @GetMapping("/write")
    public String postWriteForm(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("categoryList", classifyService.findAllCategory());
        return "template/post/post_write";
    }

    @PostMapping("/write")
    public String postWrite(@Validated @ModelAttribute("post") PostWriteForm form,
                            HttpServletRequest request,
                            @RequestParam MultipartFile file,
                            BindingResult bindingResult,
                            Model model) throws IOException {
        //필드 검증
        if (bindingResult.hasErrors()) {
            log.info("err= {}", bindingResult);
            return "template/post/post_write";
        }

        // 글쓰기
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute(Const.SESSION_USER_ID);

        postService.write(form, userId, file);
        model.addAttribute("categoryTitle", form.getCategoryTitle());

        return "template/post/notice/write_success";
    }
    @GetMapping("/modify/{postId}")
    public String postModifyForm(@PathVariable String postId, Model model, HttpServletRequest request) {
        Optional<Post> post = postService.findPostByPostId(Long.parseLong(postId));

        // 권한 체크
        HttpSession session = request.getSession(false);
        if (session.getAttribute(Const.SESSION_USER_ID) != post.get().getUser().getId()){
            return "template/post/error/authority_reject";
        }
        model.addAttribute("post", post.get());
        model.addAttribute("categoryList", classifyService.findAllCategory());

        return "template/post/post_modify";
    }

    @PostMapping("/modify/{postId}")
    public String postModify(@PathVariable String postId,
                             @Validated @ModelAttribute("post") PostWriteForm form,
                             @RequestParam MultipartFile file,
                             BindingResult bindingResult, Model model,
                             HttpServletRequest request) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("err= {}", bindingResult);
            return "template/post/post_modify";
        }

        Optional<Post> post = postService.findPostByPostId(Long.parseLong(postId));

        HttpSession session = request.getSession(false);
        if (session.getAttribute(Const.SESSION_USER_ID) != post.get().getUser().getId()){
            return "template/post/error/authority_reject";
        }

        postService.modify(form, postId, file);

        model.addAttribute("postId", postId);
        return "template/post/notice/modify_success";
    }

    @PostMapping("/delete/{postId}")
    public void postDelete(@PathVariable String postId, HttpServletRequest request) {
        Optional<Post> post = postService.findPostByPostId(Long.parseLong(postId));

        HttpSession session = request.getSession(false);
        if (session.getAttribute(Const.SESSION_USER_ID) != post.get().getUser().getId()){
            return;
        }
        postService.delete(Long.parseLong(postId));
    }
    /** 게시글 Create, Update, Delete */

    /** 댓글 Create, Update, Delete */
    @PostMapping("/{postId}/comment")
    public String commentWrite(@PathVariable String postId, Model model, @RequestParam String content, HttpServletRequest request){
        Optional<Post> post = postService.findPostByPostId(Long.parseLong(postId));
        if (post.isEmpty()){
            return "template/post/error/post_error";
        }

        HttpSession session = request.getSession(false);
        if (session.getAttribute(Const.SESSION_USER_ID) == null){
            return "template/post/error/authority_reject";
        }
        Long userId = (Long) session.getAttribute(Const.SESSION_USER_ID);
        User user = userService.findUserById(userId);
        if (user == null){
            return "template/post/error/authority_reject";
        }

        commentService.commentWrite(post.get(), user, content);

        model.addAttribute("postId", postId);

        return "template/post/notice/comment_write";
    }
    @PostMapping("/{postId}/recomment")
    public String recommentWrite(@PathVariable String postId, Model model,
                                 @RequestParam("content") String content,
                                 @RequestParam("commSeq") int commSeq,
                                 HttpServletRequest request){
        Optional<Post> post = postService.findPostByPostId(Long.parseLong(postId));
        if (post.isEmpty()){
            return "template/post/error/post_error";
        }

        HttpSession session = request.getSession(false);
        if (session.getAttribute(Const.SESSION_USER_ID) == null){
            return "template/post/error/authority_reject";
        }
        Long userId = (Long) session.getAttribute(Const.SESSION_USER_ID);
        User user = userService.findUserById(userId);
        if (user == null){
            return "template/post/error/authority_reject";
        }

        commentService.recommentWrite(post.get(), user, content, commSeq);

        model.addAttribute("postId", postId);

        return "template/post/notice/comment_write";
    }
    @PostMapping("/{postId}/comment/delete/{commSeq}/{recommSeq}")
    public void commentDelete(
            @PathVariable String postId,
            @PathVariable String commSeq,
            @PathVariable String recommSeq,
            HttpServletRequest request){

        HttpSession session = request.getSession(false);
        if (session.getAttribute(Const.SESSION_USER_ID) == null){
            return;
        }
        Long userId = (Long) session.getAttribute(Const.SESSION_USER_ID);
        User user = userService.findUserById(userId);
        if (user == null){
            return;
        }
        commentService.delete(Long.parseLong(postId), Integer.parseInt(commSeq), Integer.parseInt(recommSeq));
    }
    @PostMapping("/{postId}/comment/modify/{commSeq}/{recommSeq}")
    public String commentModify(
            @PathVariable String postId,
            @PathVariable String commSeq,
            @PathVariable String recommSeq,
            @RequestParam String content,
            Model model, HttpServletRequest request){

        HttpSession session = request.getSession(false);
        if (session.getAttribute(Const.SESSION_USER_ID) == null){
            return "template/post/error/authority_reject";
        }
        Long userId = (Long) session.getAttribute(Const.SESSION_USER_ID);
        User user = userService.findUserById(userId);
        if (user == null){
            return "template/post/error/authority_reject";
        }

        commentService.modify(Long.parseLong(postId), Integer.parseInt(commSeq), Integer.parseInt(recommSeq), content);
        model.addAttribute("postId", postId);
        return "template/post/notice/comment_write";
    }
    /** 댓글 Create, Update, Delete */

}
