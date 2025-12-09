package com.MecanicaCeleste.AutoMarketAPI.repository;

import com.MecanicaCeleste.AutoMarketAPI.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Permite buscar cliente por CPF se necessário
    Cliente findByCpf(String cpf);
}
