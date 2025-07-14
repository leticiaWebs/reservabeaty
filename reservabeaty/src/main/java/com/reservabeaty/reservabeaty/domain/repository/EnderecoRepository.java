package com.reservabeaty.reservabeaty.domain.repository;

import com.reservabeaty.reservabeaty.domain.models.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    // Buscar endereços por cidade
    List<Endereco> findByCidadeContainingIgnoreCase(String cidade);

    // Buscar endereços por bairro
    List<Endereco> findByBairroContainingIgnoreCase(String bairro);

    // Buscar endereço pelo CEP
    Optional<Endereco> findByCep(String cep);

}
