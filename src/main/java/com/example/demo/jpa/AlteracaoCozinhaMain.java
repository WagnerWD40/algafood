package com.example.demo.jpa;

import com.example.demo.DemoApplication;
import com.example.demo.domain.model.Cozinha;
import com.example.demo.domain.repository.CozinhaRepository;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class AlteracaoCozinhaMain {
    
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(DemoApplication.class)
            .web(WebApplicationType.NONE)
            .run(args);

            CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);

        Cozinha cozinha = new Cozinha();
        cozinha.setId(1L);
        cozinha.setNome("Brasileira");

        cozinhaRepository.salvar(cozinha);
    }

    

}
