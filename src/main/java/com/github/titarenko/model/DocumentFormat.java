package com.github.titarenko.model;

public enum DocumentFormat {
    DOC(".doc"),
    XLS(".xls");

    private final String name;

    DocumentFormat(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}