package com.fox.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/game")
public class FirstController {

    @GetMapping("/hello")
    public String helloPage(@RequestParam("name") String name, @RequestParam("surname") String surname, Model model) {
//        System.out.println(name + " " + surname);
        model.addAttribute("message", "Hello " + name + " " + surname);
        return "first/hello";
    }

    @GetMapping("/bye")
    public String byePage() {
        return "first/bye";
    }
}
