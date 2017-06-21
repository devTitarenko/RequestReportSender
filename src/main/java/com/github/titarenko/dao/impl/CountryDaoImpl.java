package com.github.titarenko.dao.impl;

import com.github.titarenko.dao.CountryDao;
import com.github.titarenko.model.Country;
import org.springframework.stereotype.Repository;

@Repository
public class CountryDaoImpl extends BaseObjectDaoImpl<Country> implements CountryDao {

    CountryDaoImpl() {
        super(Country.class);
    }
}