package com.MecanicaCeleste.AutoMarketAPI.controller;

import com.MecanicaCeleste.AutoMarketAPI.model.Veiculo;
import com.MecanicaCeleste.AutoMarketAPI.repository.VeiculoRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoRepository repository;

    @GetMapping
    public String listar(HttpSession session, Model model) { // Remova @RequestParam
        String papel = (String) session.getAttribute("papel");
        if (papel == null) return "redirect:/login";

        model.addAttribute("papel", papel);
        model.addAttribute("veiculos", repository.findAll());
        return "veiculos";
    }

    @GetMapping("/novo")
    public String exibirForm(HttpSession session, Model model) {
        String papel = (String) session.getAttribute("papel");
        model.addAttribute("papel", papel);
        model.addAttribute("veiculo", new Veiculo());
        return "veiculo-form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("veiculo") Veiculo veiculo, BindingResult result) {
        if (result.hasErrors()) return "veiculo-form";
        repository.save(veiculo);
        return "redirect:/veiculos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Veiculo v = repository.findById(id).orElseThrow();
        model.addAttribute("veiculo", v);
        return "veiculo-form";
    }

    @PostMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/veiculos";
    }
}
