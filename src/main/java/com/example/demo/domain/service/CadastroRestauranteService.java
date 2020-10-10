package com.example.demo.domain.service;

import com.example.demo.domain.exception.EntidadeNaoEncontradaException;
import com.example.demo.domain.model.Cozinha;
import com.example.demo.domain.model.Restaurante;
import com.example.demo.domain.repository.CozinhaRepository;
import com.example.demo.domain.repository.RestauranteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroRestauranteService {

    @Autowired
    private CozinhaRepository cozinhaRepository;
    
    @Autowired
    private RestauranteRepository restauranteRepository;

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();    
        Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
                                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                                    String.format("Não existe cadastro de cozinha com código %d", cozinhaId)
                                ));
                                    
        restaurante.setCozinha(cozinha);

        return restauranteRepository.save(restaurante);
    }

    public void excluir(Long restauranteId) {

        try {

            restauranteRepository.deleteById(restauranteId);

        } catch (EmptyResultDataAccessException e) {

            throw new EntidadeNaoEncontradaException(
                String.format("Não existe um cadastro de cozinha com código %d", restauranteId)
            );
        }
    }

}
