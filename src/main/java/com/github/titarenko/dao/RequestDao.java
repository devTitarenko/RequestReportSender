package com.github.titarenko.dao;

import com.github.titarenko.model.Request;

import java.util.Date;
import java.util.List;

public interface RequestDao extends BaseObjectDao<Request> {
    List<Request> findByDate(Date date);
}
