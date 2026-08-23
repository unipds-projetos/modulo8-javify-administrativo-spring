package br.com.unipds.javify.administrativo.controller;

import br.com.unipds.javify.administrativo.dto.PlanoRequest;
import br.com.unipds.javify.administrativo.dto.PlanoResponse;
import br.com.unipds.javify.administrativo.service.PlanoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/planos")
public class PlanoController {

    private final PlanoService planoService;

    public PlanoController(PlanoService planoService) {
        this.planoService = planoService;
    }

    @GetMapping
    public ResponseEntity<List<PlanoResponse>> listar() {
        return ResponseEntity.ok(planoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanoResponse> buscar(@PathVariable Integer id) {
        return ResponseEntity.ok(planoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PlanoResponse> criar(@RequestBody @Valid PlanoRequest request) {
        var response = planoService.criar(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanoResponse> atualizar(@PathVariable Integer id,
                                                   @RequestBody @Valid PlanoRequest request) {
        return ResponseEntity.ok(planoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        planoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
