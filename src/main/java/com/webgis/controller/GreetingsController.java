package com.webgis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.*;


@Controller
public class GreetingsController {

    @GetMapping("/greet/{name}")
    public String greet(@PathVariable String name, Model model) {
        model.addAttribute("name", name);
        return "greetings";
    }
}
