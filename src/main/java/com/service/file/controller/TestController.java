package com.service.file.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
    @RequestMapping("/api/upload")
    public void te(){
        System.out.println("1111");
    }
}
