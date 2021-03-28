package com.kenuiworks.frameworkbox.util;

import com.kenuiworks.frameworkbox.model.Framework;
import com.kenuiworks.frameworkbox.repository.FrameworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 01/02/21.
 */
@Component
public class DummyData {

    @Autowired
    FrameworkRepository repository;

    @PostConstruct
    public void savePosts(){
        List<Framework> frameworks = new ArrayList<>();

        Framework framework1 = new Framework();
        framework1.setTitulo("Spring Boot");
        framework1.setDescricao("O Spring Boot é uma ferramenta que visa facilitar o processo de configuração e publicação de aplicações que utilizem o ecossistema Spring.");
        frameworks.add(framework1);

        Framework framework2 = new Framework();
        framework2.setTitulo("Micronaut");
        framework2.setDescricao("O Micronaut é uma estrutura completa e moderna baseada em JVM para a construção de microsserviços modulares e facilmente testáveis e aplicativos sem servidor.(https://micronaut.io/).");
        frameworks.add(framework2);

        for(Framework framework: frameworks){
            repository.save(framework);
        }
    }

}
