package com.github.titarenko.controller;

import com.github.titarenko.reporter.DocumentGenerator;
import com.github.titarenko.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/get_document")
public class RequestController {

    @Autowired
    private CountryService countryService;
    @Autowired
    private DocumentGenerator documentGenerator;

    @RequestMapping(value = "/")
    @ResponseBody
    public String method(@RequestParam("email") String email,
                         @RequestParam(required = false, value = "doc_format") String format,
                         @RequestParam(required = false, value = "filter") String filter) {
        System.out.println(countryService.getAllCountriesList());
        String string = "";
        string += "email = " + email +
                "doc_format = " + format +
                "filter = " + filter;
        documentGenerator.createDoc();
        documentGenerator.createXls();
        return string;
    }



//	@RequestMapping(value = "/{email}", method = RequestMethod.GET)
//	public String getMail(@PathVariable String email, ModelMap model) {
//
//		model.addAttribute("email", email);
//		return "list";
//
//	}
//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String getDefaultMail(ModelMap model) {
//
//        model.addAttribute("email", countryService.getAllCountriesList());
//        return "list";
//    }
}