package com.MecanicaCeleste.AutoMarketAPI.repository;

import com.MecanicaCeleste.AutoMarketAPI.model.Aluguel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AluguelRepository extends JpaRepository<Aluguel, Long> {
    List<Aluguel> findByAtivoTrue(); // Para listar alugueres em curso
}
