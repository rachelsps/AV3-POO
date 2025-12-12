package com.MecanicaCeleste.AutoMarketAPI.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    // Tela de login (GET)
    @GetMapping("/login")
    public String loginPage() {
        return "login"; 
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/login";
    }

    @PostMapping("/login-process")
    public String loginSubmit(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        // ADMIN
        if ("admin".equals(username) && "123456".equals(password)) {
            session.setAttribute("papel", "ADMIN");
            return "redirect:/home";
        }

        // VENDEDOR
        if ("vendedor".equals(username) && "123456".equals(password)) {
            session.setAttribute("papel", "VENDEDOR");
            return "redirect:/home";
        }

        // LOGIN INVÁLIDO
        model.addAttribute("erro", "Usuário ou senha incorretos.");
        return "login";
    }
}