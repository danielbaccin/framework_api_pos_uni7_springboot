package com.kenuiworks.frameworkbox.exception;

public class FrameworkAlreadyRegisteredException extends Exception {

    public FrameworkAlreadyRegisteredException(String framworkName) {
        super(String.format("Framework with name %s already registered in the system.", framworkName));
    }

}
