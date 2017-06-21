package com.github.titarenko.dao;

import com.github.titarenko.model.BaseObject;

import java.io.Serializable;
import java.util.List;

public interface BaseObjectDao<T extends BaseObject> extends Serializable {

    void save(T o);

    void delete(T o);

    T find(long id);

    List<T> getAll();
}
