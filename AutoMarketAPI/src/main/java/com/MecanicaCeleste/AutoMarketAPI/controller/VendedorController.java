package com.MecanicaCeleste.AutoMarketAPI.controller;

import com.MecanicaCeleste.AutoMarketAPI.model.Vendedor;
import com.MecanicaCeleste.AutoMarketAPI.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuarios") // Mapeado conforme usuários.html
public class VendedorController {

    @Autowired
    private VendedorRepository repository;

    // Listar todos os usuários/vendedores
    @GetMapping
    public String listar(Model model) {
        List<Vendedor> vendedores = repository.findAll();
        model.addAttribute("usuarios", vendedores);
        return "usuarios"; // Retorna usuarios.html
    }

    // Exibir formulário de novo vendedor
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("usuario", new Vendedor());
        return "usuario-form"; // Retorna usuario-form.html
    }

    // Salvar ou Editar
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("usuario") Vendedor vendedor) {
        repository.save(vendedor);
        return "redirect:/usuarios";
    }

    // Abrir para edição
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Vendedor v = repository.findById(id).orElseThrow();
        model.addAttribute("usuario", v);
        return "usuario-form";
    }

    // Excluir vendedor
    @PostMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/usuarios";
    }
}