package com.MecanicaCeleste.AutoMarketAPI.controller;

import com.MecanicaCeleste.AutoMarketAPI.model.Usuario;
import com.MecanicaCeleste.AutoMarketAPI.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository repository;

    public UsuarioController(UsuarioRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String listar(Model model, HttpSession session) {
        model.addAttribute("papel", session.getAttribute("papel"));
        model.addAttribute("usuarios", repository.findAll());
        return "usuarios";
    }

    @GetMapping("/novo")
    public String novo(Model model, HttpSession session) {
        model.addAttribute("papel", session.getAttribute("papel"));
        model.addAttribute("usuario", new Usuario());
        return "usuario-form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, HttpSession session) {
        model.addAttribute("papel", session.getAttribute("papel"));
        model.addAttribute("usuario", repository.findById(id).orElseThrow());
        return "usuario-form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("usuario") Usuario usuario,
                         BindingResult result,
                         Model model,
                         HttpSession session,
                         RedirectAttributes ra) {

        if (result.hasErrors()) {
            model.addAttribute("papel", session.getAttribute("papel"));
            return "usuario-form";
        }

        repository.save(usuario);
        ra.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");
        return "redirect:/usuarios";
    }

    @PostMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id, RedirectAttributes ra) {
        repository.deleteById(id);
        ra.addFlashAttribute("mensagem", "Usuário removido com sucesso!");
        return "redirect:/usuarios";
    }
}