package com.MecanicaCeleste.AutoMarketAPI.controller;

import com.MecanicaCeleste.AutoMarketAPI.dto.administrador.DadosAdministrador;
import com.MecanicaCeleste.AutoMarketAPI.model.Administrador;
import com.MecanicaCeleste.AutoMarketAPI.repository.AdministradorRespository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/administradores")
public class AdministradorController {

    private final AdministradorRespository repository;

    public AdministradorController(AdministradorRespository repository) {
        this.repository = repository;
    }

    // Listagem de administradores
    @GetMapping
    public String listar(Model model) {
        List<Administrador> admins = repository.findAll();
        model.addAttribute("usuarios", admins); // Usando a mesma variável do usuários.html
        model.addAttribute("tipoUsuario", "Administrador");
        return "usuarios"; // Reutiliza a tela usuários.html
    }

    // Formulário para novo administrador
    @GetMapping("/novo")
    public String exibirForm(Model model) {
        model.addAttribute("usuario", new Administrador());
        return "usuario-form"; // Reutiliza a tela usuario-form.html
    }

    // Salvar novo ou edição
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("usuario") DadosAdministrador dto, BindingResult result) {
        if (result.hasErrors()) {
            return "usuario-form";
        }

        Administrador admin = new Administrador();
        if (dto.getId() != null) admin.setId(dto.getId()); // Se for edição
        admin.setEmail(dto.getEmail());
        admin.setSenha(dto.getSenha());

        repository.save(admin);
        return "redirect:/administradores";
    }

    // Abrir para edição
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Administrador admin = repository.findById(id).orElseThrow();
        model.addAttribute("usuario", admin);
        return "usuario-form";
    }

    // Exclusão
    @PostMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/administradores";
    }
}