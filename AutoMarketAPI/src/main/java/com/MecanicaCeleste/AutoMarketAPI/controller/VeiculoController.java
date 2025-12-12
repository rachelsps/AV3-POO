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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoRepository repository;

    // LISTAR VEÍCULOS
    @GetMapping
    public String listar(HttpSession session, Model model) {
        String papel = (String) session.getAttribute("papel");
        if (papel == null) return "redirect:/login";

        List<Veiculo> veiculos = repository.findAll();

        model.addAttribute("papel", papel);
        model.addAttribute("veiculos", veiculos);

        return "veiculos";
    }

    // FORMULÁRIO NOVO VEÍCULO
    @GetMapping("/novo")
    public String exibirForm(HttpSession session, Model model) {
        String papel = (String) session.getAttribute("papel");
        if (papel == null) return "redirect:/login";

        model.addAttribute("papel", papel);
        model.addAttribute("veiculo", new Veiculo());

        return "veiculo-form";
    }

    // SALVAR (NOVO / EDIÇÃO)
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("veiculo") Veiculo veiculo,
                         BindingResult result,
                         HttpSession session,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        String papel = (String) session.getAttribute("papel");
        model.addAttribute("papel", papel);

        // Se tiver erro de validação, volta para o formulário
        if (result.hasErrors()) {
            return "veiculo-form";
        }

        repository.save(veiculo);

        // Mensagem de sucesso enviada para /veiculos
        redirectAttributes.addFlashAttribute(
                "mensagem",
                "Veículo cadastrado com sucesso!"
        );

        return "redirect:/veiculos";
    }

    // EDITAR VEÍCULO
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id,
                         HttpSession session,
                         Model model) {

        String papel = (String) session.getAttribute("papel");
        if (papel == null) return "redirect:/login";

        Veiculo v = repository.findById(id).orElseThrow();

        model.addAttribute("papel", papel);
        model.addAttribute("veiculo", v);

        return "veiculo-form";
    }

    // EXCLUIR VEÍCULO
    @PostMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id,
                          RedirectAttributes redirectAttributes) {

        repository.deleteById(id);

        redirectAttributes.addFlashAttribute(
                "mensagem",
                "Veículo removido com sucesso!"
        );

        return "redirect:/veiculos";
    }
}