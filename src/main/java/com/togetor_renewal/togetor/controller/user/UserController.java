package com.togetor_renewal.togetor.controller.user;

import com.togetor_renewal.togetor.domain.entity.User;
import com.togetor_renewal.togetor.domain.repository.UserRepository;
import com.togetor_renewal.togetor.validation.user.UserJoinForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/join")
    public String joinForm(Model model){
        // 모델에 추가하지 않으면 joinForm에서 에러메시지들이 나타남
        model.addAttribute("user", new User());
        return "template/user/join";
    }

    @PostMapping("/join")
    public String join(@Validated @ModelAttribute("user") UserJoinForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        // 비밀번호 일치 확인 (GlobalError 검증 처리)
        if (!form.getPass().equals(form.getPassConfirm())){
            bindingResult.reject("passEquals");
        }

        // FieldError 검증 처리
        if (bindingResult.hasErrors()){
            log.info("errors={} ", bindingResult);
            return "template/user/join";
        }

        LocalDateTime now = LocalDateTime.now();

        // 검증 성공시 User 객체에 담아서 DB에 저장해야 된다.
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
    public String login(){
        return "template/user/login";
    }

}
