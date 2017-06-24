package com.github.titarenko.service.impl;

import com.github.titarenko.dao.UserGroupDao;
import com.github.titarenko.model.Country;
import com.github.titarenko.model.UserGroup;
import com.github.titarenko.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupServiceImpl implements UserGroupService {

    @Autowired
    private UserGroupDao userGroupDao;

    @Override
    public List<UserGroup> getAllUserGroups() {
        return userGroupDao.getAll();
    }
}