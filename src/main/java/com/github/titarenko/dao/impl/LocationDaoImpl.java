package com.github.titarenko.dao.impl;

import com.github.titarenko.dao.LocationDao;
import com.github.titarenko.model.Location;
import org.springframework.stereotype.Repository;

@Repository
public class LocationDaoImpl  extends BaseObjectDaoImpl<Location> implements LocationDao {

    LocationDaoImpl() {
        super(Location.class);
    }
}