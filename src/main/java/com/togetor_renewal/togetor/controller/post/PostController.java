package com.togetor_renewal.togetor.controller.post;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class PostController {

    @GetMapping("/posts")
    public String postList(){
        return "template/post/postList";
    }

    @GetMapping("/post/write")
    public String postWriteForm(){
        return "template/post/postWrite";
    }

}
