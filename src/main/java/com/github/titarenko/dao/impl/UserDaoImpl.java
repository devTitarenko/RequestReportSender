package com.github.titarenko.dao.impl;

import com.github.titarenko.dao.UserDao;
import com.github.titarenko.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends BaseObjectDaoImpl<User> implements UserDao{

    public UserDaoImpl() {
        super(User.class);
    }
}
