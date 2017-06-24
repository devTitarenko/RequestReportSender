package com.github.titarenko.dao.impl;

import com.github.titarenko.dao.RequestDao;
import com.github.titarenko.model.Request;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class RequestDaoImpl extends BaseObjectDaoImpl<Request> implements RequestDao {

    public RequestDaoImpl() {
        super(Request.class);
    }

    @Override
    public List<Request> findByDate(Date date) {
        return entityManager.createNamedQuery(Request.FIND_BY_DATE, Request.class)
                .setParameter("date", date).getResultList();
    }
}