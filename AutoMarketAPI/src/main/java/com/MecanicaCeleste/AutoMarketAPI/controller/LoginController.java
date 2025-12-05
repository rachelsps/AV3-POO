package com.MecanicaCeleste.AutoMarketAPI.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; 
    }

     @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String loginSubmit(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {

        // LOGIN ADMIN
        if (username.equals("admin") && password.equals("123456")) {
            return "redirect:/home?papel=ADMIN";
        }

        // LOGIN VENDEDOR
        if (username.equals("vendedor") && password.equals("123456")) {
            return "redirect:/home?papel=VENDEDOR";
        }

        // LOGIN INVÁLIDO
        model.addAttribute("erro", "Usuário ou senha incorretos.");
        return "login";
    }
}