package com.example.demo.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.example.demo.domain.model.Restaurante;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository
    extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante> {

    // exemplo de query JPA usando anotação @Query
    @Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
    List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinha);

    // exemplo de query usando arquivo xml, localizado no diretório resources/META-INT
    List<Restaurante> consultarPorNomePorXML(String nome, @Param("id") Long cozinha);

    // exemplo de implementação customizada de repositório com a classe RestauranteRepositoryImpl.
    // A classe precisa ser nomeada exatamente dessa maneira Model + "RepositoryImpl" 
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
}
