package com.github.titarenko.dao.impl;

import com.github.titarenko.dao.SessionDao;
import com.github.titarenko.model.Session;
import org.springframework.stereotype.Repository;

@Repository
public class SessionDaoImpl extends BaseObjectDaoImpl<Session> implements SessionDao{

    public SessionDaoImpl() {
        super(Session.class);
    }
}
