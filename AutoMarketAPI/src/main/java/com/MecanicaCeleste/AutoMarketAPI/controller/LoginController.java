package com.MecanicaCeleste.AutoMarketAPI.controller;

import jakarta.servlet.http.HttpSession;
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
    public String logout(HttpSession session) {
        session.invalidate(); // Limpa a memória ao sair
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username, @RequestParam String password,
                              HttpSession session, Model model) {

        // ADMIN
        if (username.equals("admin") && password.equals("123456")) {
            session.setAttribute("papel", "ADMIN"); // Salva na sessão
            return "redirect:/home";
        }

        // VENDEDOR
        if (username.equals("vendedor") && password.equals("123456")) {
            session.setAttribute("papel", "VENDEDOR"); // Salva na sessão
            return "redirect:/home";
        }

        model.addAttribute("erro", "Usuário ou senha incorretos.");
        return "login";
    }
}