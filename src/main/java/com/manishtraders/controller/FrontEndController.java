package com.manishtraders.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/ui")
public class FrontEndController {

    @GetMapping("/greeting")
    public String index(Model model, @RequestParam(defaultValue = "Customer") String name){
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/h")
    public String welcome(){
        return "home";
    }

    @GetMapping("/home1")
    public String welcomeHome(){
        return "home";
    }

    @GetMapping("/login2")
    public String login(){
        return "login";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Hello, Welcome";
    }

}
