package com.viniciuscardoso.arch.vraptor.dao.common;

import com.viniciuscardoso.arch.vraptor.domain.common.AbstractGenericEntity;
import com.viniciuscardoso.arch.vraptor.exception.DaoException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static ch.lambdaj.Lambda.join;

/**
 * Project: arch
 * User: Vinicius Cardoso
 * Date: 28/03/14
 * Time: 14:38
 */

public abstract class AbstractGenericDao<T extends AbstractGenericEntity> {

    //<editor-fold desc="[Attributes, Constructors, Setters/Getters]">
    private final Session session;
    private Class<T> classe;
    private Logger logger;

    @SuppressWarnings("unchecked")
    public AbstractGenericDao(Session session, Class clazz) {
        this.session = session;
        this.classe = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.logger = Logger.getLogger(clazz);
    }

    protected final Session getSession() {
        return this.session;
    }

    protected final Logger getLogger() {
        return this.logger;
    }
    //</editor-fold>

    //<editor-fold desc="[Create: not permitted]">
    //</editor-fold>

    //<editor-fold desc="[Retrieve]">
    @SuppressWarnings("unchecked")
    public List<T> list() {
        try {
            return session.createQuery("from " + this.classe.getName()).list();
        } catch (HibernateException e) {
            throw new DaoException("Não foi possível recuperar objetos [" + this.getEntityName() + "].", e);
        }
    }

    @SuppressWarnings("unchecked")
    public T getById(Long id, String fieldName) {
        try {
            Query q = session.createQuery("from " + this.classe.getName() + " where " + fieldName + " = :id");
            q.setParameter("id", id);
            return (T) q.uniqueResult();
        } catch (HibernateException e) {
            throw new DaoException("Não foi possível carregar objeto [" + this.getEntityName() + "].", e);
        }
    }

    @SuppressWarnings("unchecked")
    public T getById(Integer id, String fieldName) {
        try {
            Query q = session.createQuery("from " + this.classe.getName() + " where " + fieldName + " = :id");
            q.setParameter("id", id);
            return (T) q.uniqueResult();
        } catch (HibernateException e) {
            throw new DaoException("Não foi possível carregar objeto [" + this.getEntityName() + "].", e);
        }
    }

    @SuppressWarnings("unchecked")
    public T getById(String id, String fieldName) {
        try {
            Query q = session.createQuery("from " + this.classe.getName() + " where " + fieldName + " = :id");
            q.setParameter("id", id);
            return (T) q.uniqueResult();
        } catch (HibernateException e) {
            throw new DaoException("Não foi possível carregar objeto [" + this.getEntityName() + "].", e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> getList(List<Long> ids) {
        try {
            if (ids != null && ids.size() > 0) {
                Query q = session.createQuery("from " + this.classe.getName() + " where id in (:ids)");
                q.setParameterList("ids", ids);
                return q.list();
            } else {
                return Collections.emptyList();
            }
        } catch (HibernateException e) {
            throw new DaoException("Não foi possível carregar objeto [" + this.getEntityName() + "].", e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> getList(ArrayList<Integer> ids) { //clash type, same erasure...
        try {
            if (ids != null && ids.size() > 0) {
                Query q = session.createQuery("from " + this.classe.getName() + " where id in (:ids)");
                q.setParameterList("ids", ids);
                return q.list();
            } else {
                return Collections.emptyList();
            }
        } catch (HibernateException e) {
            throw new DaoException("Não foi possível carregar objeto [" + this.getEntityName() + "].", e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> getList(Collection<String> ids) { //clash type, same erasure...
        try {
            if (ids != null && ids.size() > 0) {
                Query q = session.createQuery("from " + this.classe.getName() + " where id in (:ids)");
                q.setParameterList("ids", ids);
                return q.list();
            } else {
                return Collections.emptyList();
            }
        } catch (HibernateException e) {
            throw new DaoException("Não foi possível carregar objeto [" + this.getEntityName() + "].", e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> listSorted(String... listFields) {
        String q = "from " + this.classe.getName();
        if (listFields.length > 0) q += " order by " + join(listFields, ", ");

        try {
            return session.createQuery(q).list();
        } catch (HibernateException e) {
            throw new DaoException("Não foi possível recuperar objetos [" + this.getEntityName() + "].", e);
        }
    }
    //</editor-fold>

    //<editor-fold desc="[Update: not permitted]">
    //</editor-fold>

    //<editor-fold desc="[Delete: not permitted]">
    //</editor-fold>

    //<editor-fold desc="[Private methods]">
    private String getEntityName() {
        return this.classe.getSimpleName();
    }

    private Class<T> getEntityClass() {
        return this.classe;
    }
    //</editor-fold>
}
