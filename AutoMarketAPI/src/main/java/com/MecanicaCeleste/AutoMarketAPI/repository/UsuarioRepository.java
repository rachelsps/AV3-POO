package com.MecanicaCeleste.AutoMarketAPI.repository;

import com.MecanicaCeleste.AutoMarketAPI.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}