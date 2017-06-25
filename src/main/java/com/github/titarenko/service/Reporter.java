package com.github.titarenko.service;

import com.github.titarenko.model.DocumentFormat;

import java.util.Date;

public interface Reporter {
    void createReport(DocumentFormat documentFormat, Date dateFilter);
}
