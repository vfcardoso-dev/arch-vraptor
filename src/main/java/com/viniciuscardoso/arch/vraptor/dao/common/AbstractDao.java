package com.viniciuscardoso.arch.vraptor.dao.common;

import com.viniciuscardoso.arch.vraptor.domain.common.AbstractEntity;
import com.viniciuscardoso.arch.vraptor.exception.DaoException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import static ch.lambdaj.Lambda.join;

/**
 * Project: arch
 * User: Vinicius Cardoso
 * Date: 28/03/14
 * Time: 14:38
 */

public abstract class AbstractDao<T extends AbstractEntity> {

    //<editor-fold desc="[Attributes, Constructors, Setters/Getters]">
	private final Logger logger;
    private final Session session;
    private Class<T> classe;

    public AbstractDao(Session session, Class clazz) {
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

    //<editor-fold desc="[Create]">
    public void save(T entity) {
        try {
            session.getTransaction().begin();
            session.save(entity);
            session.getTransaction().commit();
            logger.info("Objeto [" + this.getEntityName() + "] adicionado com id = " + String.valueOf(entity.getId()));
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            logger.info("Erro ao adicionar objeto. " + e.getMessage());
            throw new DaoException("Não foi possível criar o objeto [" + this.getEntityName() + "].", e);
        }
    }

    public void save(List<T> lista) {
        try {
            session.getTransaction().begin();
            for (int i = 0; i < lista.size(); i++) {
                session.save(lista.get(i));
            }
            session.getTransaction().commit();
            logger.info("Objetos [" + this.getEntityName() + "] adicionados com sucesso.");
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            logger.info("Erro ao adicionar objeto. " + e.getMessage());
            throw new DaoException("Não foi possível criar o objeto [" + this.getEntityName() + "].", e);
        }
    }
    //</editor-fold>

    //<editor-fold desc="[Retrieve]">
    public List<T> list() {
        try {
            return session.createQuery("from " + this.classe.getName()).list();
        } catch (HibernateException e) {
            throw new DaoException("Não foi possível recuperar objetos [" + this.getEntityName() + "].", e);
        }
    }

    public T getById(Long id) {
        try {
            Query q = session.createQuery("from " + this.classe.getName() + " where id = :id");
            q.setParameter("id", id);
            return (T) q.uniqueResult();
        } catch (HibernateException e) {
            throw new DaoException("Não foi possível carregar objeto [" + this.getEntityName() + "].", e);
        }
    }

    public List<T> getList(List<Long> ids) {
        try {
            Query q = session.createQuery("from " + this.classe.getName() + " where id in (:ids)");
            q.setParameterList("ids", ids);
            return q.list();
        } catch (HibernateException e) {
            throw new DaoException("Não foi possível carregar objeto [" + this.getEntityName() + "].", e);
        }
    }

    public List<T> listOrdered(String[] listFields) {
        String q = "from " + this.classe.getName();
        if (listFields.length > 0) q += " order by " + join(listFields, ", ");

        try {
            return session.createQuery(q).list();
        } catch (HibernateException e) {
            throw new DaoException("Não foi possível recuperar objetos [" + this.getEntityName() + "].", e);
        }
    }
    //</editor-fold>

    //<editor-fold desc="[Update]">
    public void update(T entity) {
        try {
            session.getTransaction().begin();
            session.merge(entity);
            session.getTransaction().commit();
            logger.info("Objeto [" + this.getEntityName() + "] atualizado com id = " + String.valueOf(entity.getId()));
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            logger.info("Erro ao atualizar objeto. " + e.getMessage());
            throw new DaoException("Não foi possível atualizar o objeto [" + this.getEntityName() + "].", e);
        }
    }
    //</editor-fold>

    //<editor-fold desc="[Delete]">
    public void remove(T entity) {
        try {
            session.getTransaction().begin();
            session.delete(entity);
            session.getTransaction().commit();
            logger.info("Objeto [" + this.getEntityName() + "] removido com id = " + String.valueOf(entity.getId()));
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            logger.info("Erro ao atualizar objeto. " + e.getMessage());
            throw new DaoException("Não foi possível excluir objeto [" + this.getEntityName() + "].", e);
        }
    }
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
