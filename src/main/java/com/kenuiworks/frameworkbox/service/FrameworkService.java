package com.kenuiworks.frameworkbox.service;

import com.kenuiworks.frameworkbox.model.Framework;

import java.util.List;

public interface FrameworkService {
    List<Framework> findAll();

    Framework findById(Long id);
}
