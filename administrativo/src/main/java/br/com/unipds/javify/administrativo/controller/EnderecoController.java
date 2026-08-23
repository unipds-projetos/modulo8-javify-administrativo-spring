package br.com.unipds.javify.administrativo.controller;

import br.com.unipds.javify.administrativo.dto.EnderecoRequest;
import br.com.unipds.javify.administrativo.dto.EnderecoResponse;
import br.com.unipds.javify.administrativo.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping
    public ResponseEntity<List<EnderecoResponse>> listar() {
        return ResponseEntity.ok(enderecoService.listarTodos());
    }

    @GetMapping("/{codigoPostal}")
    public ResponseEntity<EnderecoResponse> buscar(@PathVariable String codigoPostal) {
        return ResponseEntity.ok(enderecoService.buscarPorCodigoPostal(codigoPostal));
    }

    @PostMapping
    public ResponseEntity<EnderecoResponse> criar(@RequestBody @Valid EnderecoRequest request) {
        var response = enderecoService.criar(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{codigoPostal}")
                .buildAndExpand(response.codigoPostal())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{codigoPostal}")
    public ResponseEntity<EnderecoResponse> atualizar(@PathVariable String codigoPostal,
                                                      @RequestBody @Valid EnderecoRequest request) {
        return ResponseEntity.ok(enderecoService.atualizar(codigoPostal, request));
    }

    @DeleteMapping("/{codigoPostal}")
    public ResponseEntity<Void> remover(@PathVariable String codigoPostal) {
        enderecoService.remover(codigoPostal);
        return ResponseEntity.noContent().build();
    }
}
