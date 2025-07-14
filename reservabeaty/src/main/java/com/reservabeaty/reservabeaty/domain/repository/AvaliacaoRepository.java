package com.reservabeaty.reservabeaty.domain.repository;

import com.reservabeaty.reservabeaty.domain.models.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    // Buscar avaliações por estabelecimento
    List<Avaliacao> findByEstabelecimento_Id(Long estabelecimentoId);

    // Buscar avaliações por profissional
    List<Avaliacao> findByProfissional_Id(Long profissionalId);

    // Buscar avaliações de um cliente específico
    List<Avaliacao> findByCliente_Id(Long clienteId);
 }

