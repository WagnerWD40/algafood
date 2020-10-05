package com.example.demo.api.controller;

import java.util.List;

import com.example.demo.domain.model.Estado;
import com.example.demo.domain.repository.EstadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estados")
public class EstadoController {
    
    @Autowired
    private EstadoRepository estadoRepository;

    @GetMapping
    private List<Estado> listar() {
        return estadoRepository.listar();
    }

}
