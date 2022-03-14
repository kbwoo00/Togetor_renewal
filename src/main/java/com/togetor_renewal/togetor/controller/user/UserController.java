package com.togetor_renewal.togetor.controller.user;

import com.togetor_renewal.togetor.validation.user.UserJoinForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
    @GetMapping("/join")
    public String joinForm(){
        return "template/user/join";
    }

    @PostMapping("/join")
    public String join(@Validated @ModelAttribute UserJoinForm form, BindingResult bindingResult){
        log.info(form.toString());
        // 검증 처리 로직

        // 검증 성공시
        // user 객체에 넘겨줘야한다!
        return "redirect:/user/join";
    }

    @GetMapping("/login")
    public String login(){
        return "template/user/login";
    }

}
