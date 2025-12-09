package com.MecanicaCeleste.AutoMarketAPI.controller;

import com.MecanicaCeleste.AutoMarketAPI.model.Aluguel;
import com.MecanicaCeleste.AutoMarketAPI.model.Veiculo;
import com.MecanicaCeleste.AutoMarketAPI.repository.AluguelRepository;
import com.MecanicaCeleste.AutoMarketAPI.repository.VeiculoRepository;
import com.MecanicaCeleste.AutoMarketAPI.repository.ClienteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.math.BigDecimal;

@Controller
@RequestMapping("/alugueis")
public class AluguelController {

    private final AluguelRepository aluguelRepository;
    private final VeiculoRepository veiculoRepository;
    private final ClienteRepository clienteRepository;

    public AluguelController(AluguelRepository aluguelRepository, VeiculoRepository veiculoRepository, ClienteRepository clienteRepository) {
        this.aluguelRepository = aluguelRepository;
        this.veiculoRepository = veiculoRepository;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("alugueis", aluguelRepository.findAll());
        return "alugueis"; // Espera alugueis.html
    }

    @PostMapping("/novo")
    public String novoAluguel(@RequestParam Long veiculoId, @RequestParam Long clienteId, @ModelAttribute Aluguel aluguel) {
        Veiculo v = veiculoRepository.findById(veiculoId).orElseThrow();

        // Verifica se há estoque para alugar
        if (v.getEstoque() > 0) {
            aluguel.setVeiculo(v);
            aluguel.setCliente(clienteRepository.findById(clienteId).orElseThrow());
            aluguelRepository.save(aluguel);

            // Diminui o estoque disponível
            v.setEstoque(v.getEstoque() - 1);
            veiculoRepository.save(v);
        }
        return "redirect:/alugueis";
    }

    @GetMapping("/novo")
    public String exibirFormularioNovo(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("veiculos", veiculoRepository.findAll());
        return "aluguel-form";
    }

    @PostMapping("/devolver/{id}")
    public String devolverVeiculo(@PathVariable Long id) {
        Aluguel a = aluguelRepository.findById(id).orElseThrow();
        a.setAtivo(false);

        // Lógica de cálculo de valor total
        long dias = ChronoUnit.DAYS.between(a.getDataInicio(), LocalDate.now());
        a.setValorTotal(a.getValorDiaria().multiply(BigDecimal.valueOf(Math.max(dias, 1))));

        aluguelRepository.save(a);

        // Repõe o veículo no estoque
        Veiculo v = a.getVeiculo();
        v.setEstoque(v.getEstoque() + 1);
        veiculoRepository.save(v);

        return "redirect:/alugueis";
    }
}