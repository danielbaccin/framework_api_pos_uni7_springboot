package com.kenuiworks.frameworkbox.controller;

import com.kenuiworks.frameworkbox.dto.FrameworkDTO;
import com.kenuiworks.frameworkbox.dto.MonthOfExperienceDTO;
import com.kenuiworks.frameworkbox.exception.FrameworkAlreadyRegisteredException;
import com.kenuiworks.frameworkbox.exception.FrameworkNotFoundException;
import com.kenuiworks.frameworkbox.service.FrameworkService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/frameworks")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FrameworkController {

    private FrameworkService service;

    @GetMapping
    public List<FrameworkDTO> getFrameworks(){
        return service.findAll();
    }


    @GetMapping("/{name}")
    public FrameworkDTO findByName(@PathVariable String name) throws FrameworkNotFoundException {
        return service.findByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FrameworkDTO createFramework(@RequestBody @Valid FrameworkDTO frameworkDTO) throws FrameworkAlreadyRegisteredException {
        return service.createFramework(frameworkDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws FrameworkNotFoundException {
        service.deleteById(id);
    }

    @PatchMapping("/{id}/increment/month/experience")
    public FrameworkDTO increment(@PathVariable Long id, @RequestBody @Valid MonthOfExperienceDTO quantityDTO) throws FrameworkNotFoundException {
        return service.incrementMonthOfExperience(id, quantityDTO.getMonthsOfExperience());
    }


}
