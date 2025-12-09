package com.MecanicaCeleste.AutoMarketAPI.repository;

import com.MecanicaCeleste.AutoMarketAPI.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    // Busca customizada para o filtro da tela veiculos.html
    List<Veiculo> findByModeloContainingAndMarcaContaining(String modelo, String marca);
}
