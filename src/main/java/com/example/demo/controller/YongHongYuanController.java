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
    
    @RequestMapping("list2")
    public String list2() {
        return "5L";
    }
    
    @RequestMapping("notdrink")
    public String notdrink() {
        return "too cold, dont drink";
    }
    
    @RequestMapping("canDrink")
    public String canDrink() {
        return "晚来天欲雪，能饮一杯否";
    }
    
    @RequestMapping("closed")
    public String closed() {
        return "closed";
    }
    
}
