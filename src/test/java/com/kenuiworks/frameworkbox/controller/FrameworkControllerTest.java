package com.kenuiworks.frameworkbox.controller;

import com.kenuiworks.frameworkbox.builder.FrameworkDTOBuilder;
import com.kenuiworks.frameworkbox.dto.FrameworkDTO;
import com.kenuiworks.frameworkbox.exception.FrameworkNotFoundException;
import com.kenuiworks.frameworkbox.service.FrameworkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static com.kenuiworks.frameworkbox.util.JsonConvertionUtil.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class FrameworkControllerTest    {

    private static final String FRAMEWORK_API_URL_PATH = "/api/v1/frameworks";
    private static final long VALID_ID = 1L;
    private static final long INVALID_ID = 2l;
    public static final String NAME_INVALIDO = "nome invalido";

    @Mock
    private FrameworkService service;

    @InjectMocks
    private FrameworkController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void deveriaCriarFrameworkQuandoPOSTChamado() throws Exception {
        // given
        FrameworkDTO frameworkDTO = FrameworkDTOBuilder.builder().build().toFrameworkDTO();

        // when
        when(service.createFramework(frameworkDTO)).thenReturn(frameworkDTO);

        // then
        mockMvc.perform(post(FRAMEWORK_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(frameworkDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(frameworkDTO.getName())))
                .andExpect(jsonPath("$.description", is(frameworkDTO.getDescription())))
                .andExpect(jsonPath("$.language", is(frameworkDTO.getLanguage())))
                .andExpect(jsonPath("$.satisfactionLevel", is(frameworkDTO.getSatisfactionLevel().toString())));
    }

    @Test
    void deveriaLancarExceptionAoTentarCriarFrameworkSemCampoObriatorio() throws Exception {
        // given
        FrameworkDTO beerDTO = FrameworkDTOBuilder.builder().build().toFrameworkDTO();
        beerDTO.setDescription(null);

        // then
        mockMvc.perform(post(FRAMEWORK_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(beerDTO)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void deveriaRetornarFrameworkQuandoChamadoComNomeValido() throws Exception {
        // given
        FrameworkDTO frameworkDTO = FrameworkDTOBuilder.builder().build().toFrameworkDTO();

        //when
        when(service.findByName(frameworkDTO.getName())).thenReturn(frameworkDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(FRAMEWORK_API_URL_PATH + "/" + frameworkDTO.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(frameworkDTO.getName())))
                .andExpect(jsonPath("$.description", is(frameworkDTO.getDescription())))
                .andExpect(jsonPath("$.language", is(frameworkDTO.getLanguage())))
                .andExpect(jsonPath("$.satisfactionLevel", is(frameworkDTO.getSatisfactionLevel().toString())));
    }


    @Test
    void deveriaRetornarFrameworkNotFoundQuandoChamadoComNomeInvalido() throws Exception {
        //when
        when(service.findByName(NAME_INVALIDO)).thenThrow(FrameworkNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(FRAMEWORK_API_URL_PATH + "/" + NAME_INVALIDO)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void deveriaListarFrameworksQuandoGETChamado() throws Exception {
        // given
        FrameworkDTO frameworkDTO = FrameworkDTOBuilder.builder().build().toFrameworkDTO();

        //when
        when(service.findAll()).thenReturn(Collections.singletonList(frameworkDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(FRAMEWORK_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(frameworkDTO.getName())))
                .andExpect(jsonPath("$[0].description", is(frameworkDTO.getDescription())))
                .andExpect(jsonPath("$[0].language", is(frameworkDTO.getLanguage())))
                .andExpect(jsonPath("$[0].satisfactionLevel", is(frameworkDTO.getSatisfactionLevel().toString())));
    }


    @Test
    void deveriaReceberNoContentQuandoChamadoDeleteComIdValido() throws Exception {
        //when
        doNothing().when(service).deleteById(VALID_ID);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(FRAMEWORK_API_URL_PATH + "/" + VALID_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveriaReceberNotFoundQuandoChamadoDeleteComIdiNValido() throws Exception {
        //when
        doThrow(FrameworkNotFoundException.class).when(service).deleteById(INVALID_ID);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(FRAMEWORK_API_URL_PATH + "/" + INVALID_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }




}
