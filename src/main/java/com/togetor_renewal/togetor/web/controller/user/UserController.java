package com.togetor_renewal.togetor.web.controller.user;

import com.togetor_renewal.togetor.domain.entity.User;
import com.togetor_renewal.togetor.domain.repository.UserRepository;
import com.togetor_renewal.togetor.web.Const;
import com.togetor_renewal.togetor.web.service.user.LoginService;
import com.togetor_renewal.togetor.web.validation.user.UserJoinForm;
import com.togetor_renewal.togetor.web.validation.user.UserLoginForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final LoginService loginService;
    private final UserRepository userRepository;

    @GetMapping("/join")
    public String joinForm(Model model){
        // 모델에 추가하지 않으면 joinForm에서 에러메시지들이 나타남
        model.addAttribute("user", new User());
        return "template/user/join";
    }

    @PostMapping("/join")
    public String join(@Validated @ModelAttribute("user") UserJoinForm form, BindingResult bindingResult){

        // 비밀번호 일치 확인 (GlobalError 검증 처리)
        if (!form.getPass().equals(form.getPassConfirm())){
            bindingResult.reject("passEquals");
            return "template/user/join";
        }

        // FieldError 검증 처리
        if (bindingResult.hasErrors()){
            log.info("errors={} ", bindingResult);
            return "template/user/join";
        }

        // 아이디 중복체크
        Optional<User> dupUser = userRepository.findByEmail(form.getEmail());
        if (!dupUser.isEmpty()){
            bindingResult.rejectValue("email", "duplicated.user.email");
            return "template/user/join";
        }

        // 검증 성공시 User 객체에 담아서 DB에 저장해야 된다.
        LocalDateTime now = LocalDateTime.now();
        User user = new User(
                LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(),now.getHour(), now.getMinute(), now.getSecond()),
                form.getEmail(), form.getPass(), form.getPassConfirm(),
                form.getName(), form.getNickname(), form.getPhone(), form.getPostcode(),
                form.getAddress(), form.getDetailAddress(), form.getExtraAddress()
                );

        // 만든 User 객체를 Repository(DB)에 저장하자
        userRepository.save(user);

        return "redirect:/user/login";
    }

    @GetMapping("/login")
    public String loginFrom(Model model){
        model.addAttribute("user", new User());
        return "template/user/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("user") UserLoginForm form,
                        BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request)
    {
        if (bindingResult.hasErrors()){
            log.info("err= {}", bindingResult);
            return "template/user/login";
        }

        User loginUser = loginService.login(form.getEmail(), form.getPass());

        if (loginUser == null){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            log.info("err= {}", bindingResult);
            return "template/user/login";
        }

        // 로그인 성공 처리 세션처리
        HttpSession session = request.getSession(true); // true는 기존 세션이 있으면 세션을 반환, 없으면 신규 세션 생성
        session.setAttribute(Const.LOGIN_SESSION, loginUser);

        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){

        //세션이 있으면 기존 세션 반환, 세션이 없으면 세션 생성하지 X
        HttpSession session = request.getSession(false);

        if (session != null) session.invalidate();

        return "redirect:/";
    }



}
