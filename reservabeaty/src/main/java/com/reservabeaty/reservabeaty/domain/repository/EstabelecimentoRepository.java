package com.reservabeaty.reservabeaty.domain.repository;

import com.reservabeaty.reservabeaty.domain.models.Estabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Long> {
    // Buscar estabelecimentos por nome contendo texto (para filtro)
    List<Estabelecimento> findByNomeContainingIgnoreCase(String nome);

    // Buscar estabelecimentos por cidade
    List<Estabelecimento> findByEnderecoCidadeContainingIgnoreCase(String cidade);

    // Buscar estabelecimentos por bairro
    List<Estabelecimento> findByEnderecoBairroContainingIgnoreCase(String bairro);
}
