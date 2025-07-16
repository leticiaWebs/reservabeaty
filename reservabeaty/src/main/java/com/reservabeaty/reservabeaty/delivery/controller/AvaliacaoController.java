package com.reservabeaty.reservabeaty.delivery.controller;

import com.reservabeaty.reservabeaty.domain.models.Avaliacao;
import com.reservabeaty.reservabeaty.application.usecase.service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {
    @Autowired
    private AvaliacaoService service;

    @PostMapping
    public ResponseEntity<Avaliacao> criar(@RequestBody Avaliacao avaliacao) {
        return ResponseEntity.ok(service.salvar(avaliacao));
    }

    @GetMapping("/estabelecimento/{id}")
    public ResponseEntity<List<Avaliacao>> listarPorEstabelecimento(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorEstabelecimento(id));
    }

    @GetMapping("/profissional/{id}")
    public ResponseEntity<List<Avaliacao>> listarPorProfissional(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorProfissional(id));
    }

}
