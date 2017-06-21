package com.github.titarenko.controller;

import com.github.titarenko.dao.CountryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/get_document")
public class RequestController {

	@Autowired
	private CountryDao countryDao;

	@RequestMapping(value = "/{email}", method = RequestMethod.GET)
	public String getMail(@PathVariable String email, ModelMap model) {

		model.addAttribute("email", email);
		return "list";

	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getDefaultMail(ModelMap model) {

//		model.addAttribute("movie", "this is default movie");
		model.addAttribute("email", countryDao.find(1));
		return "list";

	}

}