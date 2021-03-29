package com.kenuiworks.frameworkbox.service;

import com.kenuiworks.frameworkbox.dto.FrameworkDTO;
import com.kenuiworks.frameworkbox.exception.FrameworkAlreadyRegisteredException;
import com.kenuiworks.frameworkbox.exception.FrameworkNotFoundException;
import com.kenuiworks.frameworkbox.mapper.FrameworkMapper;
import com.kenuiworks.frameworkbox.model.Framework;
import com.kenuiworks.frameworkbox.repository.FrameworkRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FrameworkService{

    private FrameworkRepository repository;
    private final FrameworkMapper frameworkMapper = FrameworkMapper.INSTANCE;

    public List<FrameworkDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(frameworkMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Framework findById(Long id) {
        return repository.findById(id).get();
    }

    public FrameworkDTO createFramework(FrameworkDTO frameworkDTO) throws FrameworkAlreadyRegisteredException {
        verifyIfIsAlreadyRegisteredWithName(frameworkDTO.getName());
        Framework framework = frameworkMapper.toModel(frameworkDTO);
        Framework saved = repository.save(framework);
        return frameworkMapper.toDTO(saved);
    }



    private void verifyIfIsAlreadyRegisteredWithName(String name) throws FrameworkAlreadyRegisteredException {
        Optional<Framework> optSaved = repository.findByName(name);
        if (optSaved.isPresent()) {
            throw new FrameworkAlreadyRegisteredException(name);
        }
    }

    public FrameworkDTO findByName(String name) throws FrameworkNotFoundException {
        Framework framework = repository.findByName(name)
                .orElseThrow(() -> new FrameworkNotFoundException(name));
        return frameworkMapper.toDTO(framework);
    }

    public void deleteById(Long id) throws FrameworkNotFoundException {
        Framework framework = verifyIfIsAlreadyRegisteredWithId(id);
        repository.deleteById(id);
    }

    private Framework verifyIfIsAlreadyRegisteredWithId(Long id) throws FrameworkNotFoundException {
        return repository.findById(id)
                .orElseThrow( () -> new FrameworkNotFoundException(id));
    }

    private Framework verifyIfExists(Long id) throws FrameworkNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new FrameworkNotFoundException(id));
    }
}
