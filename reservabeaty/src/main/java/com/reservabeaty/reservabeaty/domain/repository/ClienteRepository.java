package com.reservabeaty.reservabeaty.domain.repository;

import com.reservabeaty.reservabeaty.domain.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Buscar cliente por email (ex: para login ou confirmação)
    Optional<Cliente> findByEmail(String email);

    // Buscar cliente por nome contendo texto (para filtros)
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
}
