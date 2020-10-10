package com.example.demo.infrastructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.example.demo.domain.model.Restaurante;
import com.example.demo.domain.repository.RestauranteRepository;
import com.example.demo.domain.repository.RestauranteRepositoryQueries;

import static com.example.demo.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.example.demo.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {
    
    @PersistenceContext
    private EntityManager manager;

    @Autowired @Lazy
    private RestauranteRepository restauranteRepository;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        
        // fazendo uma query dinâmica, com todos os parâmetros opcionais

        var jpql = new StringBuilder();

        /* 
        * Na query abaixo 0 = 0 é sempre true, logo poderemos adicionar outros valores com AND e,
        * caso outros valores não existam, nos será retornado todos os restaurantes.
        */
        jpql.append("from Restaurante where 0 = 0 ");  

        // HashMap para estocarmos os parâmetros que foram enviados
        var parametros = new HashMap<String, Object>();

        if (StringUtils.hasLength(nome)) {
            jpql.append("and nome like :nome ");
            parametros.put("nome", "%" + nome + "%");
        }

        if (taxaFreteInicial != null) {
            jpql.append("and taxaFrete >= :taxaInicial ");
            parametros.put("taxaInicial", taxaFreteInicial);
        }

        if (taxaFreteFinal != null) {
            jpql.append("and taxaFrete <= :taxaFinal");
            parametros.put("taxaFinal", taxaFreteFinal);
        }

        TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(), Restaurante.class);

        parametros.forEach((chave, valor) -> query.setParameter(chave, valor));

        return query.getResultList();
                   
    }

    @Override
    public List<Restaurante> findWithCriteriaQuery(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        // Exemplo simples utilizando CriteriaQuery
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
        Root<Restaurante> root = criteria.from(Restaurante.class);  // equivale ao "from Restaurante"

        var predicates = new ArrayList<Predicate>();

        if (StringUtils.hasText(nome)) {
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
        }
        
        if (taxaFreteInicial != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }
        
        if (taxaFreteFinal != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }
        
        criteria.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Restaurante> query = manager.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public List<Restaurante> findComFreteGratis(String nome) {
        return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
    }

}
