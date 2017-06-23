package com.github.titarenko.service.impl;

import com.github.titarenko.dao.CountryDao;
import com.github.titarenko.model.Country;
import com.github.titarenko.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryDao countryDao;

    @Override
    public List<Country> getAllCountriesList() {
        return countryDao.getAll();
    }
}