package com.github.titarenko.service.impl;

import com.github.titarenko.dao.RequestDao;
import com.github.titarenko.model.Request;
import com.github.titarenko.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestDao requestDao;

    @Autowired
    public RequestServiceImpl(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    @Override
    public List<Request> getAllRequests() {
        return requestDao.getAll();
    }

    @Override
    public List<Request> getRequestsByDate(Date date) {
        return requestDao.findByDate(date);
    }
}
