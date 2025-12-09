package com.MecanicaCeleste.AutoMarketAPI.controller;

import com.MecanicaCeleste.AutoMarketAPI.model.Venda;
import com.MecanicaCeleste.AutoMarketAPI.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.List;

public class VendaController {
    @Autowired
    private VendaRepository vendaRepository;

    @GetMapping("/relatorios/vendas")
    public String relatorioVendas(Model model) {
        List<Venda> vendas = vendaRepository.findAll();

        BigDecimal totalVendido = vendas.stream()
                .map(Venda::getPrecoVenda)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("vendas", vendas);
        model.addAttribute("totalVendido", totalVendido);
        return "relatorio-vendas";
    }
}
