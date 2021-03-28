package com.kenuiworks.frameworkbox.service;

import com.kenuiworks.frameworkbox.dto.FrameworkDTO;
import com.kenuiworks.frameworkbox.exception.FrameworkAlreadyRegisteredException;
import com.kenuiworks.frameworkbox.mapper.FrameworkMapper;
import com.kenuiworks.frameworkbox.model.Framework;
import com.kenuiworks.frameworkbox.repository.FrameworkRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FrameworkService{

    private FrameworkRepository repository;
    private final FrameworkMapper frameworkMapper = FrameworkMapper.INSTANCE;

    public List<Framework> findAll() {
        return repository.findAll();
    }

    public Framework findById(Long id) {
        return repository.findById(id).get();
    }

    public FrameworkDTO createFramework(FrameworkDTO frameworkDTO) throws FrameworkAlreadyRegisteredException {
        verifyIfIsAlreadyRegistered(frameworkDTO.getTittle());
        Framework framework = frameworkMapper.toModel(frameworkDTO);
        Framework saved = repository.save(framework);
        return frameworkMapper.toDTO(saved);
    }



    private void verifyIfIsAlreadyRegistered(String name) throws FrameworkAlreadyRegisteredException {
        Optional<Framework> optSaved = repository.findByTittle(name);
        if (optSaved.isPresent()) {
            throw new FrameworkAlreadyRegisteredException(name);
        }
    }
}
