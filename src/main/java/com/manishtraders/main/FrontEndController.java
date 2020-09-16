package com.manishtraders.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FrontEndController {

    @RequestMapping("/greeting")
    public String index(Model model, @RequestParam(defaultValue = "Customer") String name){
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "redirect:/uploadStatus";
    }

}
