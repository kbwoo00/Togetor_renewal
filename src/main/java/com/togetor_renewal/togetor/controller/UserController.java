package com.togetor_renewal.togetor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @GetMapping("/join")
    public String join(){
        return "template/user/join";
    }
    @GetMapping("/login")
    public String login(){
        return "template/user/login";
    }
}
