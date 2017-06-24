package com.github.titarenko.service.impl;

import com.github.titarenko.dao.LocationDao;
import com.github.titarenko.model.Location;
import com.github.titarenko.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationDao locationDao;

    @Override
    public List<Location> getAllLocations() {
        return locationDao.getAll();
    }
}