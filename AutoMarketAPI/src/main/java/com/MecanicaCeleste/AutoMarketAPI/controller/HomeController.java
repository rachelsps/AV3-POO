package com.MecanicaCeleste.AutoMarketAPI.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String raiz() {
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {
        // Recupera da memória
        String papel = (String) session.getAttribute("papel");

        // Se não estiver logado, manda pro login
        if (papel == null) return "redirect:/login";

        model.addAttribute("papel", papel);
        return "home";
    }
}