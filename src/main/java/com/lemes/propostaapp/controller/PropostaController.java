package com.lemes.propostaapp.controller;

import com.lemes.propostaapp.dto.PropostaRequestDto;
import com.lemes.propostaapp.dto.PropostaResponseDto;
import com.lemes.propostaapp.service.PropostaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/proposta")
public class PropostaController {

    @Autowired
    private PropostaService propostaService;

    @PostMapping
    public ResponseEntity<PropostaResponseDto> criar(@RequestBody PropostaRequestDto requestDto) {
        PropostaResponseDto response = propostaService.criar(requestDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PropostaResponseDto>> obterProposta() {
        List<PropostaResponseDto> response = propostaService.obterProposta();
        return ResponseEntity.ok(response);
    }
}
