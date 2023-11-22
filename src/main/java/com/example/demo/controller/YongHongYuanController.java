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
    
    @RequestMapping("open")
    public String open() {
        return "open";
    }
    
    @RequestMapping("weather")
    public String weather() {
        return "warm";
    }
    
    @RequestMapping("2023new")
    public String newbeer() {
        return "warm";
    }
    
    @RequestMapping("start")
    public String justStart() {
        return "start";
    }
    
    @RequestMapping("weekend")
    public String weekend() {
        return "weekend";
    }
    
    @RequestMapping("incoming")
    public String incoming() {
        return "spring is incoming";
    }
    
    @RequestMapping("summer")
    public String summer() {
        return "summer is incoming";
    }
    @RequestMapping("wuyi")
    public String wuyi() {
        return "wuyi is incoming";
    }
    
    @RequestMapping("stop")
    public String stop() {
        return "stop drink";
    }

    @RequestMapping("wb")
    public String whiteBeer() {
        return "QD whiteBeer";
    }

    @RequestMapping("ltns")
    public String ltns() {
        return "long time no see";
    }
    
    @RequestMapping("shiyi")
    public String shiyi() {
        return "too fast";
    }

     
    @RequestMapping("life")
    public String life() {
        return "today is not easy";
    }
    @RequestMapping("erguotou")
    public String erguotou() {
        return "tomorrow is more diffcult";
    }

    @RequestMapping("knowledge")
    public String knowledge() {
        return "knowledge list";
    }
}
