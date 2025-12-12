package com.MecanicaCeleste.AutoMarketAPI.controller;

import com.MecanicaCeleste.AutoMarketAPI.model.Cliente;
import com.MecanicaCeleste.AutoMarketAPI.repository.ClienteRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    // Listar todos os clientes
    @GetMapping
    public String listar(Model model, HttpSession session) {
        List<Cliente> clientes = repository.findAll();
        model.addAttribute("clientes", clientes);

        // papel para navbar
        String papel = (String) session.getAttribute("papel");
        model.addAttribute("papel", papel);

        return "clientes"; // clientes.html
    }

    // Formulário para novo cliente
    @GetMapping("/novo")
    public String exibirForm(Model model, HttpSession session) {
        model.addAttribute("cliente", new Cliente());

        String papel = (String) session.getAttribute("papel");
        model.addAttribute("papel", papel);

        return "cliente-form"; // cliente-form.html
    }

    // Salvar novo ou edição
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("cliente") Cliente cliente,
                         BindingResult result,
                         HttpSession session,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        // Se tiver erro de validação, volta para o formulário
        if (result.hasErrors()) {
            String papel = (String) session.getAttribute("papel");
            model.addAttribute("papel", papel);
            return "cliente-form";
        }

        
        repository.save(cliente);

        // mensagem de sucesso 
        redirectAttributes.addFlashAttribute(
                "mensagem",
                "Cliente adicionado com sucesso!"
        );

        return "redirect:/clientes";
    }

    // Editar cliente existente
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, HttpSession session) {
        Cliente c = repository.findById(id).orElseThrow();
        model.addAttribute("cliente", c);

        String papel = (String) session.getAttribute("papel");
        model.addAttribute("papel", papel);

        return "cliente-form";
    }

    // Excluir cliente
    @PostMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/clientes";
    }
}