package com.manishtraders.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FrontEndController {

    @GetMapping("/greeting")
    public String index(Model model, @RequestParam(defaultValue = "Customer") String name){
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/")
    public String welcome(){
        return "home";
    }

    @GetMapping("/home")
    public String welcomeHome(){
        return "home";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Hello, Welcome";
    }

}
