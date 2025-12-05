package com.MecanicaCeleste.AutoMarketAPI.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

   
    @GetMapping("/")
    public String raiz() {
        return "redirect:/login";
    }

  
    @GetMapping("/home")
    public String homePage(@RequestParam(required = false) String papel, Model model) {

        
        if (papel == null) {
            papel = "VENDEDOR";
        }

        model.addAttribute("papel", papel);
        return "home"; 
    }
}