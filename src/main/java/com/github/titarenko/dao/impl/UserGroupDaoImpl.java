package com.github.titarenko.dao.impl;

import com.github.titarenko.dao.UserGroupDao;
import com.github.titarenko.model.UserGroup;
import org.springframework.stereotype.Repository;

@Repository
public class UserGroupDaoImpl extends BaseObjectDaoImpl<UserGroup> implements UserGroupDao{

    public UserGroupDaoImpl() {
        super(UserGroup.class);
    }
}
