package com.github.titarenko.dao.impl;

import com.github.titarenko.dao.BaseObjectDao;
import com.github.titarenko.model.BaseObject;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Transactional
public class BaseObjectDaoImpl<T extends BaseObject> implements BaseObjectDao<T> {
    private Class<T> type;

    @PersistenceContext
    EntityManager entityManager;

    BaseObjectDaoImpl(Class<T> type) {
        this.type = type;
    }

    @Override
    public void save(T object) {
        if (object.getId() == null) {
            entityManager.persist(object);
        } else {
            entityManager.merge(object);
        }
    }

    @Override
    public T find(long id) {
        return entityManager.find(type, id);
    }

    @Override
    public List<T> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = criteriaBuilder.createQuery(type);
        Root<T> root = criteria.from(type);
        criteria.orderBy(criteriaBuilder.asc(root.get("id")));
        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public void delete(T object) {
        entityManager.remove(entityManager.merge(object));
    }
}