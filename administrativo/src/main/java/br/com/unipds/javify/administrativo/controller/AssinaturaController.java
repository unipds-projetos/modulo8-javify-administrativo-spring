package br.com.unipds.javify.administrativo.controller;

import br.com.unipds.javify.administrativo.dto.AssinaturaRequest;
import br.com.unipds.javify.administrativo.dto.AssinaturaResponse;
import br.com.unipds.javify.administrativo.service.AssinaturaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/assinaturas")
public class AssinaturaController {

    private final AssinaturaService assinaturaService;

    public AssinaturaController(AssinaturaService assinaturaService) {
        this.assinaturaService = assinaturaService;
    }

    @GetMapping
    public ResponseEntity<List<AssinaturaResponse>> listar() {
        return ResponseEntity.ok(assinaturaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssinaturaResponse> buscar(@PathVariable Integer id) {
        return ResponseEntity.ok(assinaturaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<AssinaturaResponse> criar(@RequestBody @Valid AssinaturaRequest request) {
        var response = assinaturaService.criar(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssinaturaResponse> atualizar(@PathVariable Integer id,
                                                        @RequestBody @Valid AssinaturaRequest request) {
        return ResponseEntity.ok(assinaturaService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        assinaturaService.remover(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Integer id) {
        assinaturaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

}
