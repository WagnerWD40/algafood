package com.example.demo.domain.repository;

import com.example.demo.domain.model.FormaPagamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormaPagamentroRepository extends JpaRepository<FormaPagamento, Long> {

}
