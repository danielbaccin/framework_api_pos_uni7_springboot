package com.kenuiworks.frameworkbox.service;

import com.kenuiworks.frameworkbox.builder.FrameworkDTOBuilder;
import com.kenuiworks.frameworkbox.dto.FrameworkDTO;
import com.kenuiworks.frameworkbox.exception.FrameworkAlreadyRegisteredException;
import com.kenuiworks.frameworkbox.exception.FrameworkNotFoundException;
import com.kenuiworks.frameworkbox.mapper.FrameworkMapper;
import com.kenuiworks.frameworkbox.model.Framework;
import com.kenuiworks.frameworkbox.repository.FrameworkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
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

        when(repository.findByName(frameworkDTO.getName())).thenReturn(Optional.empty());
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

        when(repository.findByName(framework.getName())).thenReturn(Optional.of(framework));

        assertThrows(FrameworkAlreadyRegisteredException.class, () -> service.createFramework(frameworkDTO));
    }

    @Test
    public void deveriaListarTodosFrameworks(){

        FrameworkDTO frameworkDTO = FrameworkDTOBuilder.builder().build().toFrameworkDTO();
        Framework framework = mapper.toModel(frameworkDTO);

        when(repository.findAll()).thenReturn(Collections.singletonList(framework));

        List<FrameworkDTO> allFrameworksDTO = service.findAll();

        assertThat(allFrameworksDTO, is(not(empty())));
        assertThat(allFrameworksDTO.get(0), is(equalTo(frameworkDTO)));

    }


    @Test
    void deveriaRetornarListaVazia() {
        when(repository.findAll()).thenReturn(Collections.EMPTY_LIST);

        List<FrameworkDTO> listaVazia = service.findAll();

        assertThat(listaVazia, is(empty()));
    }


    @Test
    void deveriaRetornarFrameworkQndPesquisadoPorNome() throws FrameworkNotFoundException {
        FrameworkDTO frameworkDTO = FrameworkDTOBuilder.builder().build().toFrameworkDTO();
        Framework framework = mapper.toModel(frameworkDTO);

        when(repository.findByName(frameworkDTO.getName())).thenReturn(Optional.of(framework));

        FrameworkDTO byName = service.findByName(frameworkDTO.getName());

        assertThat(byName.getId(), is(equalTo(frameworkDTO.getId())));
        assertThat(byName.getDescription(), is(equalTo(frameworkDTO.getDescription())));
        assertThat(byName.getSatisfactionLevel(), is(equalTo(frameworkDTO.getSatisfactionLevel())));

    }

    @Test
    void deveriaLancarExceptionQndPesquisadoPorNomeInexistente() throws FrameworkNotFoundException {
        FrameworkDTO frameworkDTO = FrameworkDTOBuilder.builder().build().toFrameworkDTO();

        when(repository.findByName(frameworkDTO.getName())).thenReturn(Optional.empty());

        assertThrows(FrameworkNotFoundException.class, () -> service.findByName(frameworkDTO.getName()));
    }

    @Test
    void deveriaDeletarQuandoInformadoIdValido() throws FrameworkNotFoundException {

        FrameworkDTO frameworkDTO = FrameworkDTOBuilder.builder().build().toFrameworkDTO();
        Framework framework = mapper.toModel(frameworkDTO);

        when(repository.findById(framework.getId())).thenReturn(Optional.of(framework));
        doNothing().when(repository).deleteById(frameworkDTO.getId());

        service.deleteById(frameworkDTO.getId());

        Mockito.verify(repository, Mockito.times(1)).findById(frameworkDTO.getId());
        Mockito.verify(repository, Mockito.times(1)).deleteById(frameworkDTO.getId());

    }


}
