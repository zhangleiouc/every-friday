package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/yongHongYuan")
public class YongHongYuanController {
    
    @RequestMapping("hello")
    public String hello() {
        return "world";
    }
}
