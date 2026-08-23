package br.com.unipds.javify.administrativo.controller;

import br.com.unipds.javify.administrativo.dto.UsuarioTelefoneRequest;
import br.com.unipds.javify.administrativo.dto.UsuarioTelefoneResponse;
import br.com.unipds.javify.administrativo.service.UsuarioTelefoneService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UsuarioTelefoneController {

    private final UsuarioTelefoneService usuarioTelefoneService;

    public UsuarioTelefoneController(UsuarioTelefoneService usuarioTelefoneService) {
        this.usuarioTelefoneService = usuarioTelefoneService;
    }

    @GetMapping("/api/telefones")
    public ResponseEntity<List<UsuarioTelefoneResponse>> listar() {
        return ResponseEntity.ok(usuarioTelefoneService.listarTodos());
    }

    @GetMapping("/api/telefones/{id}")
    public ResponseEntity<UsuarioTelefoneResponse> buscar(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioTelefoneService.buscarPorId(id));
    }

    @GetMapping("/api/usuarios/{usuarioId}/telefones")
    public ResponseEntity<List<UsuarioTelefoneResponse>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(usuarioTelefoneService.listarPorUsuario(usuarioId));
    }

    @PostMapping("/api/telefones")
    public ResponseEntity<UsuarioTelefoneResponse> criar(@RequestBody @Valid UsuarioTelefoneRequest request) {
        var response = usuarioTelefoneService.criar(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PostMapping("/api/usuarios/{usuarioId}/telefones")
    public ResponseEntity<UsuarioTelefoneResponse> criarPorUsuario(@PathVariable Long usuarioId,
                                                                   @RequestBody @Valid UsuarioTelefoneRequest request) {
        var response = usuarioTelefoneService.criar(usuarioId, request);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/telefones/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/api/telefones/{id}")
    public ResponseEntity<UsuarioTelefoneResponse> atualizar(@PathVariable Integer id,
                                                             @RequestBody @Valid UsuarioTelefoneRequest request) {
        return ResponseEntity.ok(usuarioTelefoneService.atualizar(id, request));
    }

    @DeleteMapping("/api/telefones/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        usuarioTelefoneService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
