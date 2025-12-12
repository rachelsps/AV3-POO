package com.MecanicaCeleste.AutoMarketAPI.controller;

import com.MecanicaCeleste.AutoMarketAPI.dto.administrador.DadosAdministrador;
import com.MecanicaCeleste.AutoMarketAPI.model.Administrador;
import com.MecanicaCeleste.AutoMarketAPI.repository.AdministradorRespository;
import jakarta.servlet.http.HttpSession;
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
    public String listar(Model model, HttpSession session) {
        List<Administrador> admins = repository.findAll();
        model.addAttribute("usuarios", admins); // usado em usuarios.html
        model.addAttribute("tipoUsuario", "Administrador");

        // manda o papel para navbar e para o th:if do main
        String papel = (String) session.getAttribute("papel");
        model.addAttribute("papel", papel);

        return "usuarios"; // usuarios.html
    }

    // Formulário para novo administrador
    @GetMapping("/novo")
    public String exibirForm(Model model, HttpSession session) {
        model.addAttribute("usuario", new Administrador());

        String papel = (String) session.getAttribute("papel");
        model.addAttribute("papel", papel);

        return "usuario-form"; // usuario-form.html
    }

    // Salvar novo ou edição
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("usuario") DadosAdministrador dto,
                         BindingResult result,
                         Model model,
                         HttpSession session) {

        String papel = (String) session.getAttribute("papel");
        model.addAttribute("papel", papel);

        if (result.hasErrors()) {
            return "usuario-form";
        }

        Administrador admin = new Administrador();
        if (dto.getId() != null) {
            admin.setId(dto.getId()); // edição
        }
        admin.setEmail(dto.getEmail());
        admin.setSenha(dto.getSenha());

        repository.save(admin);
        return "redirect:/administradores";
    }

    // Abrir para edição
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, HttpSession session) {
        Administrador admin = repository.findById(id).orElseThrow();
        model.addAttribute("usuario", admin);

        String papel = (String) session.getAttribute("papel");
        model.addAttribute("papel", papel);

        return "usuario-form";
    }

    // Exclusão
    @PostMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/administradores";
    }
}