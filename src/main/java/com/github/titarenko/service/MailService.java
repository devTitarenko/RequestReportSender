package com.github.titarenko.service;

import com.github.titarenko.model.DocumentFormat;

public interface MailService {

    void sendEmail(String email, DocumentFormat format);
}
