package br.com.unipds.javify.administrativo.service;

import br.com.unipds.javify.administrativo.domain.Endereco;
import br.com.unipds.javify.administrativo.dto.EnderecoRequest;
import br.com.unipds.javify.administrativo.dto.EnderecoResponse;
import br.com.unipds.javify.administrativo.repository.EnderecoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public List<EnderecoResponse> listarTodos() {
        return enderecoRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public EnderecoResponse buscarPorCodigoPostal(String codigoPostal) {
        return enderecoRepository.findById(codigoPostal)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado: " + codigoPostal));
    }

    @Transactional
    public EnderecoResponse criar(EnderecoRequest request) {
        var endereco = new Endereco();
        endereco.setCodigoPostal(request.codigoPostal());
        endereco.setLogradouro(request.logradouro());
        endereco.setBairro(request.bairro());
        return toResponse(enderecoRepository.save(endereco));
    }

    @Transactional
    public EnderecoResponse atualizar(String codigoPostal, EnderecoRequest request) {
        var endereco = enderecoRepository.findById(codigoPostal)
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado: " + codigoPostal));
        endereco.setLogradouro(request.logradouro());
        endereco.setBairro(request.bairro());
        return toResponse(enderecoRepository.save(endereco));
    }

    @Transactional
    public void remover(String codigoPostal) {
        if (!enderecoRepository.existsById(codigoPostal)) {
            throw new EntityNotFoundException("Endereço não encontrado: " + codigoPostal);
        }
        enderecoRepository.deleteById(codigoPostal);
    }

    EnderecoResponse toResponse(Endereco endereco) {
        if (endereco == null) {
            return null;
        }
        return new EnderecoResponse(
                endereco.getCodigoPostal(),
                endereco.getLogradouro(),
                endereco.getBairro()
        );
    }
}
