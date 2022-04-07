package com.togetor_renewal.togetor.web.controller.post;

import com.togetor_renewal.togetor.domain.DTO.post.CommentDTO;
import com.togetor_renewal.togetor.domain.entity.Post;
import com.togetor_renewal.togetor.domain.DTO.post.PostWriteForm;
import com.togetor_renewal.togetor.domain.entity.User;
import com.togetor_renewal.togetor.web.Const;
import com.togetor_renewal.togetor.web.service.comment.CommentService;
import com.togetor_renewal.togetor.web.service.post.BookmarkService;
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
    private final BookmarkService bookmarkService;

    @GetMapping("/{postId}")
    public String post(@PathVariable String postId, Model model, HttpServletRequest request) {
        Optional<Post> post = postService.findPostByPostId(Long.parseLong(postId));
        // 해당 게시글 없을때 예외 페이지로 던지기
        if (post.isEmpty()) {
            return "template/post/error/post_error";
        }
        model.addAttribute("post", post.get());
        HttpSession session = request.getSession(false);

        try{
            // 글 작성한 유저와 로그인한 유저가 같은지 확인인
            if (session.getAttribute(Const.SESSION_USER_ID).equals(post.get().getUser().getId())) {
                model.addAttribute("writer", true);
            } else {
                model.addAttribute("writer", false);
            }

            // 북마크 되어있는지 확인. 세션 userId와 북마크의 userId 비교
            if (session.getAttribute(Const.SESSION_USER_ID) != null){
                Long userId = (Long) session.getAttribute(Const.SESSION_USER_ID);
                boolean isBookmark = bookmarkService.checkBookmark(Long.parseLong(postId), userId);

                model.addAttribute("isBookmark", isBookmark);
            }
        } catch (NullPointerException e){
            // 비로그인 상태이면 글 수정 권한 없음
            model.addAttribute("writer", false);
        }

        // 댓글 리스트들 불러오기
        List<CommentDTO> commentList = commentService.commentList(post.get().getId());
        model.addAttribute("commentList", commentList);

        // 조회수 업데이트
        postService.updateView(post.get().getId());

        return "template/post/post_content";
    }
    /** 북마크 추가 삭제 */
    @PostMapping("/addBookmark")
    public String addBookmark(@RequestParam String postId, @RequestParam String userId, Model model){
        bookmarkService.add(Long.parseLong(postId), Long.parseLong(userId));
        model.addAttribute("postId", postId);
        return "redirect:/post/" + postId;
    }
    @PostMapping("/delBookmark")
    public String delBookmark(@RequestParam String postId, @RequestParam String userId){
        bookmarkService.delete(Long.parseLong(postId),Long.parseLong(userId));
        return "redirect:/post/" + postId;
    }
    /** 북마크 추가 삭제 */

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
        postService.addComentCount(Long.parseLong(postId));

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

        postService.addComentCount(Long.parseLong(postId));

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
