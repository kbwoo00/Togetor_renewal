package com.togetor_renewal.togetor.web.controller.user;

import com.togetor_renewal.togetor.domain.DTO.user.FindPasswordForm;
import com.togetor_renewal.togetor.domain.entity.Post;
import com.togetor_renewal.togetor.domain.entity.User;
import com.togetor_renewal.togetor.domain.DTO.user.UserModifyForm;
import com.togetor_renewal.togetor.web.Const;
import com.togetor_renewal.togetor.web.service.post.BookmarkService;
import com.togetor_renewal.togetor.web.service.post.PostService;
import com.togetor_renewal.togetor.web.service.user.MailService;
import com.togetor_renewal.togetor.web.service.user.UserService;
import com.togetor_renewal.togetor.domain.DTO.user.UserJoinForm;
import com.togetor_renewal.togetor.domain.DTO.user.UserLoginForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final MailService mailService;
    private final PostService postService;
    private final BookmarkService bookmarkService;

    @GetMapping("/join")
    public String joinForm(Model model) {
        // 모델에 추가하지 않으면 joinForm에서 에러메시지들이 나타남
        model.addAttribute("user", new User());
        return "template/user/join";
    }

    @PostMapping("/join")
    public String join(@Validated @ModelAttribute("user") UserJoinForm form, BindingResult bindingResult) {
        // 비밀번호 일치 확인 (GlobalError 검증 처리)
        if (!form.getPass().equals(form.getPassConfirm())) {
            bindingResult.reject("passEquals");
            return "template/user/join";
        }

        // FieldError 검증 처리
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "template/user/join";
        }

        // 아이디 중복체크
        Optional<User> dupUser = userService.checkDuplicateEmail(form.getEmail());
        if (!dupUser.isEmpty()) {
            bindingResult.rejectValue("email", "duplicated.user.email");
            return "template/user/join";
        }

        // 검증 성공 시 폼에서 받은 정보들을 DB에 저장
        userService.join(form);

        return "redirect:/user/login";
    }

    @GetMapping("/login")
    public String loginFrom(Model model) {
        model.addAttribute("user", new User());
        return "template/user/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("user") UserLoginForm form,
                        BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            log.info("err= {}", bindingResult);
            return "template/user/login";
        }

        User loginUser = userService.confirmUser(form.getEmail(), form.getPass());

        if (loginUser == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            log.info("err= {}", bindingResult);
            return "template/user/login";
        }

        // 로그인 성공 처리 세션처리
        HttpSession session = request.getSession(true); // true는 기존 세션이 있으면 세션을 반환, 없으면 신규 세션 생성
        session.setAttribute(Const.LOGIN_SESSION, loginUser);
        session.setAttribute(Const.SESSION_USER_ID, loginUser.getId());

        return "redirect:" + redirectURL;
    }

    @GetMapping("/findPassword")
    public String findPassword() {
        return "/template/user/find_password";
    }

    @PostMapping("/findPassword")
    public void sendEmail(@RequestParam String name, @RequestParam String email) {
        mailService.sendNewPass(name, email);
    }

    @ResponseBody
    @PostMapping("/checkInfo")
    public FindPasswordForm checkUserByNameAndEmail(@RequestParam String name,
                                                    @RequestParam String email) {
        Optional<User> user = userService.findUserByEmailAndName(email, name);

        FindPasswordForm form = new FindPasswordForm();
        if (!user.isEmpty()) {
            form = new FindPasswordForm(user.get().getName(),
                    user.get().getEmail());
            return form;
        } else {

            return form;
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {

        //세션이 있으면 기존 세션 반환, 세션이 없으면 세션 생성하지 X
        HttpSession session = request.getSession(false);

        if (session != null) session.invalidate();

        return "redirect:/";
    }

    @GetMapping("/info/modify/{userId}")
    public String modifyForm(@PathVariable String userId, Model model,
                             HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        String sessionUserId = String.valueOf(session.getAttribute(Const.SESSION_USER_ID));

        // 내 정보를 다른 유저가 조회또는 수정하려고 할 때 막기
        if (!sessionUserId.equals(userId)) {
            response.setStatus(404);
            /**
             * TODO
             * 에러 예외페이지 처리
             */
            return "template/user/error/info_error";
        }

        // 기존 회원정보들을 보이게 수정 시 참고할 수 있도록
        User user = userService.findUserById(Long.parseLong(userId));
        model.addAttribute("user", user);

        return "/template/user/modify";
    }

    @PostMapping("/info/modify/{userId}")
    public String modify(@Validated @ModelAttribute("user") UserModifyForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("err= {}", bindingResult);
            return "/template/user/modify";
        }

        // 회원정보(현재 비밀번호)를 올바로 입력했는지 확인
        User user = userService.confirmUser(form.getEmail(), form.getPass());
        if (user == null) {
            bindingResult.reject("wrongPassword", "등록된 회원의 비밀번호와 일치하지 않습니다.");
            log.info("err= {}", bindingResult);
            return "/template/user/modify";
        }
        form.setUserId(user.getId());

        // 이메일 변경하고 싶어서 새로운 이메일 폼에 입력했을때
        if (form.getNewEmail() != null) {
            // 이메일 중복체크
            Optional<User> existUser = userService.checkDuplicateEmail(form.getNewEmail());
            if (existUser.isEmpty()) {
                form.setEmail(form.getNewEmail());
            } else {
                bindingResult.rejectValue("email", "duplicated.user.email");
                return "/template/user/modify";
            }
        }

        // 만약 비밀번호 변경하고 싶으면 새로운 비밀번호를 폼에 입력햇는지 체크
        if (form.getNewPass() != null) {
            // 비밀번호 일치여부 체크
            if (!form.getNewPass().equals(form.getNewPassConfirm())) {
                bindingResult.reject("passEquals");
                return "/template/user/modify";
            }
            form.setPass(form.getNewPass());
        }

        // 검증처리 성공시 유저정보 업데이트 로직
        userService.modifyUser(form);

        return "/template/user/notice/modify_success";
    }

    @ResponseBody
    @PostMapping("/checkDuplicate")
    public String checkDuplicateEmail(@RequestParam String newEmail) throws UnsupportedEncodingException {
        Optional<User> user = userService.checkDuplicateEmail(newEmail);
        // 중복이메일 체크해서 이메일이 있으면 "" 반환
        if (user.isEmpty()) {
            return "사용가능";
        } else {
            return "";
        }
    }

    @GetMapping("/withdrawal/{userId}")
    public String withdrawalForm(@PathVariable String userId, Model model,
                                 HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        String sessionUserId = String.valueOf(session.getAttribute(Const.SESSION_USER_ID));
        // 내 정보를 로그인한 다른 유저가 보려고 할 때 막기
        if (!sessionUserId.equals(userId)) {
            response.setStatus(404);
            /**
             * TODO
             * 에러 예외페이지 처리
             */
            return "template/user/error/info_error";
        }

        // 기존 회원정보들을 보이게 수정 시 참고할 수 있도록
        User user = userService.findUserById(Long.parseLong(userId));
        model.addAttribute("user", user);

        if (model.getAttribute(Const.SUCCESS_CHECK) != null) {
            // 회원이 무사히 탈퇴하면 alert창 띄우기 위해
            model.addAttribute("checkSuccess", true);
        }

        return "template/user/withdrawal";
    }

    @PostMapping("/withdrawal/{userId}")
    public String withdrawal(@Validated @ModelAttribute("user") UserLoginForm form,
                             BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            log.info("err= {}", bindingResult);
            return "template/user/withdrawal";
        }

        // 아이디 비밀번호가 올바른지
        User loginUser = userService.confirmUser(form.getEmail(), form.getPass());
        if (loginUser == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            log.info("err= {}", bindingResult);
            return "template/user/withdrawal";
        }

        HttpSession session = request.getSession(false);
        // 로그인한 유저와는 다른 회원을 탈퇴하려고 하면 예외페이지로
        if (session.getAttribute(Const.SESSION_USER_ID) != loginUser.getId()) {
            response.setStatus(404);
            /**
             * TODO
             * 에러 예외페이지 처리
             */
            return "/template/user/error/info_error";
        }

        session.invalidate();
        userService.withdrawalUser(loginUser.getId());

        return "/template/user/notice/withdrawal_success";
    }

    @GetMapping("/myPostList/{userId}")
    public String myPostList(@PathVariable String userId, Model model, HttpServletRequest request) {
        List<Post> postList = postService.findPostsByUserId(Long.parseLong(userId));
        model.addAttribute("postList", postList);

        HttpSession session = request.getSession(false);
        if ( (Long)(session.getAttribute(Const.SESSION_USER_ID)) != Long.parseLong(userId)) {
            return "/template/post/error/authority_reject";
        }

        return "/template/user/mypost_list";
    }

    @GetMapping("/bookmarkList/{userId}")
    public String bookmarkList(@PathVariable String userId, Model model, HttpServletRequest request) {

        List<Post> postList = postService.findBookmarkPostList(Long.parseLong(userId));
        model.addAttribute("postList", postList);

        return "/template/user/bookmark_list";
    }
}
