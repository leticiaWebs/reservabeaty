package com.reservabeaty.reservabeaty.delivery.controller;

import com.reservabeaty.reservabeaty.domain.models.Estabelecimento;
import com.reservabeaty.reservabeaty.application.usecase.service.EstabelecimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estabelecimentos")
public class EstabelecimentoController {

    @Autowired
    private EstabelecimentoService service;

    @PostMapping
    public ResponseEntity<Estabelecimento> criar(@RequestBody Estabelecimento estabelecimento) {
        return ResponseEntity.ok(service.criar(estabelecimento));
    }

    @GetMapping
    public ResponseEntity<List<Estabelecimento>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estabelecimento> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estabelecimento> atualizar(@PathVariable Long id, @RequestBody Estabelecimento atualizado) {
        return ResponseEntity.ok(service.atualizar(id, atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/nome")
    public ResponseEntity<List<Estabelecimento>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(service.buscarPorNome(nome));
    }

    @GetMapping("/buscar/cidade")
    public ResponseEntity<List<Estabelecimento>> buscarPorCidade(@RequestParam String cidade) {
        return ResponseEntity.ok(service.buscarPorCidade(cidade));
    }

    @GetMapping("/buscar/bairro")
    public ResponseEntity<List<Estabelecimento>> buscarPorBairro(@RequestParam String bairro) {
        return ResponseEntity.ok(service.buscarPorBairro(bairro));
    }
}
