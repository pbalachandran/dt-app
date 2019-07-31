package com.corelogic.dt.domain;

public class ProjectNotFoundException extends Exception {

    private String message;

    public ProjectNotFoundException(String message) {
        super(message);
    }
}
