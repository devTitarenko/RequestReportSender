package com.github.titarenko.service.impl;

import com.github.titarenko.dao.SessionDao;
import com.github.titarenko.model.Session;
import com.github.titarenko.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionDao sessionDao;

    @Override
    public List<Session> getAllSessions(){
        return sessionDao.getAll();
    }
}
