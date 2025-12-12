package com.MecanicaCeleste.AutoMarketAPI.controller;

import com.MecanicaCeleste.AutoMarketAPI.model.Cliente;
import com.MecanicaCeleste.AutoMarketAPI.model.Usuario;
import com.MecanicaCeleste.AutoMarketAPI.model.Veiculo;
import com.MecanicaCeleste.AutoMarketAPI.model.Venda;
import com.MecanicaCeleste.AutoMarketAPI.repository.ClienteRepository;
import com.MecanicaCeleste.AutoMarketAPI.repository.UsuarioRepository;
import com.MecanicaCeleste.AutoMarketAPI.repository.VeiculoRepository;
import com.MecanicaCeleste.AutoMarketAPI.repository.VendaRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @Autowired
    private UsuarioRepository usuarioRepository;

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
        List<Veiculo> itens = getCarrinho(session);

        BigDecimal total = itens.stream()
                .map(Veiculo::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("itens", itens);
        model.addAttribute("total", total);

        // Clientes no select
        model.addAttribute("clientes", clienteRepository.findAll());

        // TODOS os usuários cadastrados (ADMIN e VENDEDOR)
        model.addAttribute("usuariosSistema", usuarioRepository.findAll());

        // Papel para navbar
        model.addAttribute("papel", session.getAttribute("papel"));

        return "carrinho";
    }

    @GetMapping("/adicionar/{id}")
    public String adicionar(@PathVariable Long id,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {

        Veiculo veiculo = veiculoRepository.findById(id).orElseThrow();
        getCarrinho(session).add(veiculo);

        redirectAttributes.addFlashAttribute("mensagem", "Item adicionado ao carrinho!");
        return "redirect:/veiculos";
    }

    @PostMapping("/remover/{id}")
    public String remover(@PathVariable Long id, HttpSession session) {
        getCarrinho(session).removeIf(v -> v.getId().equals(id));
        return "redirect:/carrinho";
    }

    @PostMapping("/finalizar")
    @Transactional
    public String finalizar(HttpSession session,
                            @RequestParam(required = false) Long clienteId,
                            @RequestParam(required = false) Long vendedorId,
                            RedirectAttributes redirectAttributes) {

        List<Veiculo> itens = getCarrinho(session);

        if (itens.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensagem", "Carrinho vazio!");
            return "redirect:/carrinho";
        }

        // Cliente (pode ser null)
        Cliente cliente = null;
        if (clienteId != null) {
            cliente = clienteRepository.findById(clienteId).orElse(null);
        }

        // Vendedor (USUARIO do sistema) - pode ser null se não selecionar
        Usuario vendedor = null;
        if (vendedorId != null) {
            vendedor = usuarioRepository.findById(vendedorId).orElse(null);
        }

        for (Veiculo item : itens) {

            // baixa no estoque (antes de salvar a venda)
            Veiculo vBanco = veiculoRepository.findById(item.getId()).orElseThrow();
            if (vBanco.getEstoque() <= 0) {
                redirectAttributes.addFlashAttribute(
                        "mensagem",
                        "Não foi possível finalizar: o veículo " + vBanco.getModelo() + " está sem estoque."
                );
                return "redirect:/carrinho";
            }

            vBanco.setEstoque(vBanco.getEstoque() - 1);
            veiculoRepository.save(vBanco);

            // salva venda
            Venda venda = new Venda();
            venda.setVeiculo(vBanco); // usa o do banco
            venda.setPrecoVenda(vBanco.getPreco());
            venda.setCliente(cliente);
            venda.setVendedor(vendedor);

            vendaRepository.save(venda);
        }

        session.removeAttribute("carrinho");
        redirectAttributes.addFlashAttribute("mensagem", "Venda realizada com sucesso!");
        return "redirect:/carrinho";
    }
}
