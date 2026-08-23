package br.com.unipds.javify.administrativo.controller;

import br.com.unipds.javify.administrativo.dto.CartaoCreditoRequest;
import br.com.unipds.javify.administrativo.dto.CartaoCreditoResponse;
import br.com.unipds.javify.administrativo.service.CartaoCreditoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class CartaoCreditoController {

    private final CartaoCreditoService cartaoCreditoService;

    public CartaoCreditoController(CartaoCreditoService cartaoCreditoService) {
        this.cartaoCreditoService = cartaoCreditoService;
    }

    @GetMapping("/api/cartoes")
    public ResponseEntity<List<CartaoCreditoResponse>> listar() {
        return ResponseEntity.ok(cartaoCreditoService.listarTodos());
    }

    @GetMapping("/api/cartoes/{id}")
    public ResponseEntity<CartaoCreditoResponse> buscar(@PathVariable Integer id) {
        return ResponseEntity.ok(cartaoCreditoService.buscarPorId(id));
    }

    @GetMapping("/api/assinaturas/{assinaturaId}/cartoes")
    public ResponseEntity<List<CartaoCreditoResponse>> listarPorAssinatura(@PathVariable Integer assinaturaId) {
        return ResponseEntity.ok(cartaoCreditoService.listarPorAssinatura(assinaturaId));
    }

    @PostMapping("/api/cartoes")
    public ResponseEntity<CartaoCreditoResponse> criar(@RequestBody @Valid CartaoCreditoRequest request) {
        var response = cartaoCreditoService.criar(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PostMapping("/api/assinaturas/{assinaturaId}/cartoes")
    public ResponseEntity<CartaoCreditoResponse> criarPorAssinatura(@PathVariable Integer assinaturaId,
                                                                   @RequestBody @Valid CartaoCreditoRequest request) {
        var response = cartaoCreditoService.criar(assinaturaId, request);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/cartoes/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/api/cartoes/{id}")
    public ResponseEntity<CartaoCreditoResponse> atualizar(@PathVariable Integer id,
                                                           @RequestBody @Valid CartaoCreditoRequest request) {
        return ResponseEntity.ok(cartaoCreditoService.atualizar(id, request));
    }

    @DeleteMapping("/api/cartoes/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        cartaoCreditoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
