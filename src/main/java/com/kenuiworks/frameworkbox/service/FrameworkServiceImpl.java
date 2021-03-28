package com.kenuiworks.frameworkbox.service;

import com.kenuiworks.frameworkbox.model.Framework;
import com.kenuiworks.frameworkbox.repository.FrameworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FrameworkServiceImpl implements FrameworkService{

    @Autowired
    private FrameworkRepository repository;

    @Override
    public List<Framework> findAll() {
        return repository.findAll();
    }

    @Override
    public Framework findById(Long id) {
        return repository.findById(id).get();
    }
}
