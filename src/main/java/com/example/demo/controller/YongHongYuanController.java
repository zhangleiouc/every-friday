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
    
    @RequestMapping("world")
    public String world() {
        return "world";
    }
    
    @RequestMapping("buy")
    public String buy() {
        return "2.5L";
    }
    
    @RequestMapping("list")
    public String list() {
        return "2.5L";
    }
    
    @RequestMapping("notdrink")
    public String list() {
        return "too cold, dont drink";
    }
}
