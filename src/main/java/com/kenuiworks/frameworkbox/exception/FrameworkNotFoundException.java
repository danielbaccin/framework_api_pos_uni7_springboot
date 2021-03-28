package com.kenuiworks.frameworkbox.exception;

public class FrameworkNotFoundException extends Exception {

    public FrameworkNotFoundException(String name) {
        super(String.format("Framework with name %s not found in the system.", name));
    }

    public FrameworkNotFoundException(Long id) {
        super(String.format("Beer with id %s not found in the system.", id));
    }
}
