package com.reservabeaty.reservabeaty.usecase.service;

import com.reservabeaty.reservabeaty.domain.models.Avaliacao;
import com.reservabeaty.reservabeaty.domain.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoService {


    @Autowired
    private AvaliacaoRepository repository;

    public Avaliacao salvar(Avaliacao avaliacao) {
        return repository.save(avaliacao);
    }

    public List<Avaliacao> listarPorEstabelecimento(Long estabelecimentoId) {
        return repository.findByEstabelecimento_Id(estabelecimentoId);
    }

    public List<Avaliacao> listarPorProfissional(Long profissionalId) {
        return repository.findByProfissional_Id(profissionalId);
    }

    public List<Avaliacao> listarPorCliente(Long clienteId) {
        return repository.findByCliente_Id(clienteId);
    }
}

