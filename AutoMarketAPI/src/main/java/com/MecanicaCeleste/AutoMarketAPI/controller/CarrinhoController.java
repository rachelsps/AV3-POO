package com.MecanicaCeleste.AutoMarketAPI.controller;

import com.MecanicaCeleste.AutoMarketAPI.model.Cliente;
import com.MecanicaCeleste.AutoMarketAPI.model.Veiculo;
import com.MecanicaCeleste.AutoMarketAPI.model.Venda;
import com.MecanicaCeleste.AutoMarketAPI.repository.ClienteRepository;
import com.MecanicaCeleste.AutoMarketAPI.repository.VeiculoRepository;
import com.MecanicaCeleste.AutoMarketAPI.repository.VendaRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @SuppressWarnings("unchecked")
    private List<Veiculo> getCarrinho(HttpSession session) {
        List<Veiculo> itens = (List<Veiculo>) session.getAttribute("carrinho");
        if (itens == null) {
            itens = new ArrayList<>();
            session.setAttribute("carrinho", itens);
        }
        return itens;
    }

    @GetMapping
    public String exibirCarrinho(HttpSession session, Model model) {
        // Recupera itens da sessão
        List<Veiculo> itens = getCarrinho(session);

        // Calcula total
        BigDecimal total = itens.stream()
                .map(Veiculo::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("itens", itens);
        model.addAttribute("total", total);

        // Envia lista de clientes para o SELECT do formulário
        model.addAttribute("clientes", clienteRepository.findAll());

        // Garante que a navbar funcione
        String papel = (String) session.getAttribute("papel");
        model.addAttribute("papel", papel);

        return "carrinho";
    }

    @GetMapping("/adicionar/{id}")
    public String adicionar(@PathVariable Long id, HttpSession session) {
        Veiculo veiculo = veiculoRepository.findById(id).orElseThrow();
        getCarrinho(session).add(veiculo);
        return "redirect:/veiculos";
    }

    @PostMapping("/remover/{id}")
    public String remover(@PathVariable Long id, HttpSession session) {
        getCarrinho(session).removeIf(v -> v.getId().equals(id));
        return "redirect:/carrinho";
    }

    @PostMapping("/desconto")
    public String aplicarDesconto(@RequestParam BigDecimal porcentagem, HttpSession session, Model model) {
        List<Veiculo> itens = getCarrinho(session);

        BigDecimal total = itens.stream()
                .map(Veiculo::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Lógica de cálculo
        BigDecimal valorDesconto = total.multiply(porcentagem.divide(new BigDecimal(100)));
        BigDecimal totalFinal = total.subtract(valorDesconto);

        // Atualiza a tela mantendo os dados
        model.addAttribute("itens", itens);
        model.addAttribute("total", totalFinal);
        model.addAttribute("clientes", clienteRepository.findAll()); // Recarrega clientes

        String papel = (String) session.getAttribute("papel");
        model.addAttribute("papel", papel);

        return "carrinho";
    }

    @PostMapping("/finalizar")
    @Transactional
    public String finalizar(HttpSession session, @RequestParam(required = false) Long clienteId) {
        List<Veiculo> itens = getCarrinho(session);

        // Busca o cliente SE um ID foi selecionado
        Cliente cliente = null;
        if (clienteId != null) {
            cliente = clienteRepository.findById(clienteId).orElse(null);
        }

        for (Veiculo item : itens) {
            Venda venda = new Venda();
            venda.setVeiculo(item);
            venda.setPrecoVenda(item.getPreco());
            venda.setCliente(cliente); // Salva o cliente (ou null se for venda direta)

            vendaRepository.save(venda);

            // Baixa no estoque
            Veiculo vBanco = veiculoRepository.findById(item.getId()).orElseThrow();
            if (vBanco.getEstoque() > 0) {
                vBanco.setEstoque(vBanco.getEstoque() - 1);
                veiculoRepository.save(vBanco);
            }
        }

        session.removeAttribute("carrinho");
        return "redirect:/home";
    }
}