package com.MecanicaCeleste.AutoMarketAPI.controller;

import com.MecanicaCeleste.AutoMarketAPI.model.Cliente;
import com.MecanicaCeleste.AutoMarketAPI.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    // Listar todos os clientes
    @GetMapping
    public String listar(Model model) {
        List<Cliente> clientes = repository.findAll();
        model.addAttribute("clientes", clientes);
        return "clientes"; // Retorna clientes.html
    }

    // Formulário para novo cliente
    @GetMapping("/novo")
    public String exibirForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cliente-form"; // Retorna cliente-form.html
    }

    // Salvar novo ou edição
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("cliente") Cliente cliente) {
        repository.save(cliente);
        return "redirect:/clientes";
    }

    // Editar cliente existente
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Cliente c = repository.findById(id).orElseThrow();
        model.addAttribute("cliente", c);
        return "cliente-form";
    }

    // Excluir cliente
    @PostMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/clientes";
    }
}