package com.reservabeaty.reservabeaty.domain.repository;

import com.reservabeaty.reservabeaty.domain.models.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Long> {


    // Buscar serviços por nome (busca parcial e ignorando maiúsculas/minúsculas)
    List<Servico> findByNomeContainingIgnoreCase(String nome);

    // Buscar serviços por faixa de preço
    List<Servico> findByPrecoBetween(Double min, Double max);

    // Buscar serviços de um determinado estabelecimento
    List<Servico> findByEstabelecimentoId(Long estabelecimentoId);
}
