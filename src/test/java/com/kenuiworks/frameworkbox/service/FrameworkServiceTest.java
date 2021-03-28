package com.kenuiworks.frameworkbox.service;

import com.kenuiworks.frameworkbox.builder.FrameworkDTOBuilder;
import com.kenuiworks.frameworkbox.dto.FrameworkDTO;
import com.kenuiworks.frameworkbox.exception.FrameworkAlreadyRegisteredException;
import com.kenuiworks.frameworkbox.mapper.FrameworkMapper;
import com.kenuiworks.frameworkbox.model.Framework;
import com.kenuiworks.frameworkbox.repository.FrameworkRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FrameworkServiceTest {

    @Mock
    private FrameworkRepository repository;
    @InjectMocks
    private FrameworkService service;

    private final FrameworkMapper mapper = FrameworkMapper.INSTANCE;

    @Test
    public void deveriaSalvarUmNovoFramework() throws FrameworkAlreadyRegisteredException {

        FrameworkDTO frameworkDTO = FrameworkDTOBuilder.builder().build().toFrameworkDTO();
        Framework framework = mapper.toModel(frameworkDTO);

        when(repository.findByTittle(frameworkDTO.getTittle())).thenReturn(Optional.empty());
        when(repository.save(framework)).thenReturn(framework);

        FrameworkDTO frameworkCreated = service.createFramework(frameworkDTO);

        assertThat(frameworkCreated.getId(), is(equalTo(frameworkDTO.getId())));
        assertThat(frameworkCreated.getDescription(), is(equalTo(frameworkDTO.getDescription())));
        assertThat(frameworkCreated.getSatisfactionLevel(), is(equalTo(frameworkDTO.getSatisfactionLevel())));
    }

    @Test
    public void deveriaLancarExceptionAoSalvarUmFrameworkJaExistente() throws FrameworkAlreadyRegisteredException {

        FrameworkDTO frameworkDTO = FrameworkDTOBuilder.builder().build().toFrameworkDTO();
        Framework framework = mapper.toModel(frameworkDTO);

        when(repository.findByTittle(framework.getTittle())).thenReturn(Optional.of(framework));

        assertThrows(FrameworkAlreadyRegisteredException.class, () -> service.createFramework(frameworkDTO));
    }


}
