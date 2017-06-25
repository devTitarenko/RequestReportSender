package com.github.titarenko.controller;

import com.github.titarenko.dao.impl.RequestDaoImpl;
import com.github.titarenko.model.DocumentFormat;
import com.github.titarenko.service.MailService;
import com.github.titarenko.service.Reporter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;

@Controller
@RequestMapping("/get_document")
public class MainController {

    @Autowired
    private Reporter reporter;
    @Autowired
    private MailService mailService;

    private static final Logger LOGGER = Logger.getLogger(RequestDaoImpl.class);

    @RequestMapping(value = "/")
    public String requestWithParams(ModelMap model,
                                    @RequestParam("email") String email,
                                    @RequestParam(required = false, value = "doc_format") String format,
                                    @RequestParam(required = false, value = "filter") String filter) {

        String message = "email: " + email +
                ", doc format: " + format +
                ", filter: " + filter;
        LOGGER.info("Input parameters: " + message);

        DocumentFormat documentFormat = DocumentFormat.DOC;
        Date dateFilter = null;

        boolean validParams = true;
        if (format != null) {
            try {
                documentFormat = DocumentFormat.valueOf(format.toUpperCase());
            } catch (IllegalArgumentException ignored) {
                message = "Invalid document format";
                validParams = false;
            }
        }
        if (filter != null) {
            try {
                dateFilter = Date.valueOf(filter);
            } catch (IllegalArgumentException ignored) {
                message = "Invalid date filter";
                validParams = false;
            }
        }

        if (validParams) {
            reporter.createReport(documentFormat, dateFilter);
            mailService.sendEmail(email, documentFormat);
            model.addAttribute("success", "Mail has been sent to " + email);
        }

        LOGGER.info(message);
        model.addAttribute("params", message);
        return "page";
    }

    @RequestMapping(value = "/help", method = RequestMethod.GET)
    public String help(ModelMap model) {
        model.addAttribute("params", null);
        return "page";
    }
}