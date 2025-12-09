package com.MecanicaCeleste.AutoMarketAPI.controller;

import com.MecanicaCeleste.AutoMarketAPI.model.Venda;
import com.MecanicaCeleste.AutoMarketAPI.repository.VeiculoRepository;
import com.MecanicaCeleste.AutoMarketAPI.repository.VendaRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private VendaRepository vendaRepository;

    private String validarSessao(HttpSession session, Model model) {
        String papel = (String) session.getAttribute("papel");
        if (papel == null) return "redirect:/login";
        model.addAttribute("papel", papel);
        return null; // Null significa que está tudo ok
    }

    @GetMapping
    public String homeRelatorios(HttpSession session, Model model) {
        String redirect = validarSessao(session, model);
        if (redirect != null) return redirect;

        return "relatorios";
    }

    @GetMapping("/estoque")
    public String estoque(HttpSession session, Model model) {
        String redirect = validarSessao(session, model);
        if (redirect != null) return redirect;

        model.addAttribute("veiculos", veiculoRepository.findAll());
        return "estoque";
    }

    @GetMapping("/vendas")
    public String relatorioVendas(HttpSession session, Model model) {
        String redirect = validarSessao(session, model);
        if (redirect != null) return redirect;

        List<Venda> vendas = vendaRepository.findAll();

        // Calcula o total vendido
        BigDecimal total = vendas.stream()
                .map(Venda::getPrecoVenda)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("vendas", vendas);
        model.addAttribute("totalVendido", total);

        return "relatorio-vendas";
    }
}