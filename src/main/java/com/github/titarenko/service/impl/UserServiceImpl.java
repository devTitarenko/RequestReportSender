package com.github.titarenko.service.impl;

import com.github.titarenko.dao.UserDao;
import com.github.titarenko.model.User;
import com.github.titarenko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getAllUsers() {
        return userDao.getAll();
    }
}
