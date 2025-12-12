package com.MecanicaCeleste.AutoMarketAPI.controller;

import com.MecanicaCeleste.AutoMarketAPI.model.Aluguel;
import com.MecanicaCeleste.AutoMarketAPI.model.Veiculo;
import com.MecanicaCeleste.AutoMarketAPI.repository.AluguelRepository;
import com.MecanicaCeleste.AutoMarketAPI.repository.VeiculoRepository;
import com.MecanicaCeleste.AutoMarketAPI.repository.ClienteRepository;
import jakarta.servlet.http.HttpSession;
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

    public AluguelController(
            AluguelRepository aluguelRepository,
            VeiculoRepository veiculoRepository,
            ClienteRepository clienteRepository
    ) {
        this.aluguelRepository = aluguelRepository;
        this.veiculoRepository = veiculoRepository;
        this.clienteRepository = clienteRepository;
    }

    // LISTAR TODOS OS ALUGUÉIS
    @GetMapping
    public String listar(Model model, HttpSession session) {
        model.addAttribute("alugueis", aluguelRepository.findAll());
        model.addAttribute("papel", session.getAttribute("papel"));
        return "alugueis"; // alugueis.html
    }

    // FORMULÁRIO NOVO ALUGUEL
    @GetMapping("/novo")
    public String exibirFormularioNovo(Model model, HttpSession session) {
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("veiculos", veiculoRepository.findAll());
        model.addAttribute("papel", session.getAttribute("papel"));
        return "aluguel-form";
    }

    // REGISTRAR ALUGUEL
    @PostMapping("/novo")
    public String novoAluguel(
            @RequestParam Long veiculoId,
            @RequestParam Long clienteId,
            @ModelAttribute Aluguel aluguel
    ) {
        Veiculo v = veiculoRepository.findById(veiculoId).orElseThrow();

        if (v.getEstoque() > 0) {
            aluguel.setVeiculo(v);
            aluguel.setCliente(clienteRepository.findById(clienteId).orElseThrow());
            aluguel.setAtivo(true);

            aluguelRepository.save(aluguel);

            // Diminui o estoque
            v.setEstoque(v.getEstoque() - 1);
            veiculoRepository.save(v);
        }

        return "redirect:/alugueis";
    }

    // DEVOLVER VEÍCULO
    @PostMapping("/devolver/{id}")
    public String devolverVeiculo(@PathVariable Long id) {
        Aluguel a = aluguelRepository.findById(id).orElseThrow();
        a.setAtivo(false);
        a.setDataDevolucaoEfetiva(LocalDate.now());

        long dias = ChronoUnit.DAYS.between(a.getDataInicio(), LocalDate.now());
        a.setValorTotal(
                a.getValorDiaria().multiply(BigDecimal.valueOf(Math.max(dias, 1)))
        );

        aluguelRepository.save(a);

        // Repõe o veículo no estoque
        Veiculo v = a.getVeiculo();
        v.setEstoque(v.getEstoque() + 1);
        veiculoRepository.save(v);

        return "redirect:/alugueis";
    }
}