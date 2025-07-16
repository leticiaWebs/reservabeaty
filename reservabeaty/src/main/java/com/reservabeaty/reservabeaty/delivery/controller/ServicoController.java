package com.reservabeaty.reservabeaty.delivery.controller;

import com.reservabeaty.reservabeaty.domain.models.Servico;
import com.reservabeaty.reservabeaty.application.usecase.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {
    @Autowired
    private ServicoService service;

    @PostMapping
    public ResponseEntity<Servico> criar(@RequestBody Servico servico) {
        return ResponseEntity.ok(service.criar(servico));
    }

    @GetMapping
    public ResponseEntity<List<Servico>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servico> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/nome")
    public ResponseEntity<List<Servico>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(service.buscarPorNome(nome));
    }

    @GetMapping("/preco")
    public ResponseEntity<List<Servico>> buscarPorFaixaDePreco(@RequestParam Double min, @RequestParam Double max) {
        return ResponseEntity.ok(service.buscarPorFaixaDePreco(min, max));
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    public ResponseEntity<List<Servico>> buscarPorEstabelecimento(@PathVariable Long estabelecimentoId) {
        return ResponseEntity.ok(service.buscarPorEstabelecimento(estabelecimentoId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servico> atualizar(@PathVariable Long id, @RequestBody Servico atualizado) {
        return ResponseEntity.ok(service.atualizar(id, atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
