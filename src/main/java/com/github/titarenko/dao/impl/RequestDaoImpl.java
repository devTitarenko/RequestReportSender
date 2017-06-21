package com.github.titarenko.dao.impl;

import com.github.titarenko.dao.RequestDao;
import com.github.titarenko.model.Request;
import org.springframework.stereotype.Repository;

@Repository
public class RequestDaoImpl extends BaseObjectDaoImpl<Request> implements RequestDao{

    public RequestDaoImpl() {
        super(Request.class);
    }
}
