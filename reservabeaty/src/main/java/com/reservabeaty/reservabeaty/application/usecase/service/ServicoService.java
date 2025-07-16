package com.reservabeaty.reservabeaty.application.usecase.service;


import com.reservabeaty.reservabeaty.domain.models.Servico;
import com.reservabeaty.reservabeaty.domain.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository repository;

    public Servico criar(Servico servico) {
        return repository.save(servico);
    }

    public List<Servico> listarTodos() {
        return repository.findAll();
    }

    public Servico buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
    }

    public List<Servico> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Servico> buscarPorFaixaDePreco(Double min, Double max) {
        return repository.findByPrecoBetween(min, max);
    }

    public List<Servico> buscarPorEstabelecimento(Long estabelecimentoId) {
        return repository.findByEstabelecimentoId(estabelecimentoId);
    }

    public Servico atualizar(Long id, Servico atualizado) {
        Servico servico = buscarPorId(id);
        servico.setNome(atualizado.getNome());
        servico.setDescricao(atualizado.getDescricao());
        servico.setPreco(atualizado.getPreco());
        servico.setEstabelecimentoId(atualizado.getEstabelecimentoId());
        return repository.save(servico);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
