package br.com.unipds.javify.administrativo.service;

import br.com.unipds.javify.administrativo.domain.UsuarioTelefone;
import br.com.unipds.javify.administrativo.dto.UsuarioTelefoneRequest;
import br.com.unipds.javify.administrativo.dto.UsuarioTelefoneResponse;
import br.com.unipds.javify.administrativo.repository.UsuarioRepository;
import br.com.unipds.javify.administrativo.repository.UsuarioTelefoneRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UsuarioTelefoneService {

    private final UsuarioTelefoneRepository usuarioTelefoneRepository;
    private final UsuarioRepository usuarioRepository;

    public UsuarioTelefoneService(UsuarioTelefoneRepository usuarioTelefoneRepository,
                                  UsuarioRepository usuarioRepository) {
        this.usuarioTelefoneRepository = usuarioTelefoneRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioTelefoneResponse> listarTodos() {
        return usuarioTelefoneRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public UsuarioTelefoneResponse buscarPorId(Integer id) {
        return usuarioTelefoneRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Telefone não encontrado: " + id));
    }

    public List<UsuarioTelefoneResponse> listarPorUsuario(Long usuarioId) {
        return usuarioTelefoneRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public UsuarioTelefoneResponse criar(UsuarioTelefoneRequest request) {
        return criar(request.usuarioId(), request);
    }

    @Transactional
    public UsuarioTelefoneResponse criar(Long usuarioId, UsuarioTelefoneRequest request) {
        var usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + usuarioId));
        var telefone = new UsuarioTelefone();
        telefone.setUsuario(usuario);
        telefone.setNumero(request.numero());
        telefone.setTipo(request.tipo());
        return toResponse(usuarioTelefoneRepository.save(telefone));
    }

    @Transactional
    public UsuarioTelefoneResponse atualizar(Integer id, UsuarioTelefoneRequest request) {
        var telefone = usuarioTelefoneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Telefone não encontrado: " + id));
        var usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + request.usuarioId()));
        telefone.setUsuario(usuario);
        telefone.setNumero(request.numero());
        telefone.setTipo(request.tipo());
        return toResponse(usuarioTelefoneRepository.save(telefone));
    }

    @Transactional
    public void remover(Integer id) {
        if (!usuarioTelefoneRepository.existsById(id)) {
            throw new EntityNotFoundException("Telefone não encontrado: " + id);
        }
        usuarioTelefoneRepository.deleteById(id);
    }

    private UsuarioTelefoneResponse toResponse(UsuarioTelefone telefone) {
        return new UsuarioTelefoneResponse(
                telefone.getId(),
                telefone.getUsuario().getId(),
                telefone.getNumero(),
                telefone.getTipo().name()
        );
    }
}
