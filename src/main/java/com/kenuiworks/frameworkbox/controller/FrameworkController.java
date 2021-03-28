package com.kenuiworks.frameworkbox.controller;

import com.kenuiworks.frameworkbox.model.Framework;
import com.kenuiworks.frameworkbox.service.FrameworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/frameworks")
public class FrameworkController {

    @Autowired
    private FrameworkService service;

    @GetMapping
    public List<Framework> getFrameworks(){
        List<Framework> allFrameworks = service.findAll();
        return allFrameworks;
    }

    @GetMapping("/description/{id}")
    public Framework getDescription(@PathVariable("id") Long id){
        Framework frameworkById = service.findById(id);
        return frameworkById;
    }
}
