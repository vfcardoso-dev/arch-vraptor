package com.viniciuscardoso.arch.vraptor.dao.common;

import com.viniciuscardoso.arch.vraptor.domain.common.AbstractEntity;
import com.viniciuscardoso.arch.vraptor.domain.interfaces.IActorAudited;
import com.viniciuscardoso.arch.vraptor.domain.interfaces.IAuditable;
import com.viniciuscardoso.arch.vraptor.utility.ConvertUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.joda.time.LocalDateTime;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import static ch.lambdaj.Lambda.join;

/**
 * Project: arch
 * User: Vinicius Cardoso
 * Date: 28/03/14
 * Time: 14:38
 */

public abstract class AbstractAuditableDao<T extends AbstractEntity, A extends IActorAudited> {

    //<editor-fold desc="[Attributes, Constructors, Setters/Getters]">
	private final Logger logger;
    private final Session session;
    private Class<T> classe;

    @SuppressWarnings("unchecked")
    public AbstractAuditableDao(Session session, Class<? extends AbstractAuditableDao<T, A>> clazz) {
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
    @SuppressWarnings("unchecked")
    public void save(T entity, A creator) {
        try {
            if (entity instanceof IAuditable) {
                ((IAuditable<A>)entity).setCreatedAt(new LocalDateTime());
                ((IAuditable<A>)entity).setCreatedBy(creator);
            }
            session.getTransaction().begin();
            session.save(entity);
            session.getTransaction().commit();
            logger.info("Objeto [" + this.getEntityName() + "] adicionado com id = " + String.valueOf(entity.getId()));
        } catch (Exception e) {
            session.getTransaction().rollback();
            logger.info("Erro ao adicionar objeto. " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void save(List<T> lista, A creator) {
        try {
            session.getTransaction().begin();
            for (T item : lista) {
                if (item instanceof IAuditable) {
                    ((IAuditable<A>) item).setCreatedAt(new LocalDateTime());
                    ((IAuditable<A>) item).setCreatedBy(creator);
                }
                session.save(item);
            }
            session.getTransaction().commit();
            logger.info("Objetos [" + this.getEntityName() + "] adicionados com sucesso.");
        } catch (Exception e) {
            session.getTransaction().rollback();
            logger.info("Erro ao adicionar objeto. " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void forceSave(T entity) {
        try {
            session.getTransaction().begin();
            session.save(entity);
            session.getTransaction().commit();
            logger.info("Objeto [" + this.getEntityName() + "] adicionado com id = " + String.valueOf(entity.getId()));
        } catch (Exception e) {
            session.getTransaction().rollback();
            logger.info("Erro ao adicionar objeto. " + e.getMessage());
        }
    }
    //</editor-fold>

    //<editor-fold desc="[Retrieve]">
    @SuppressWarnings("unchecked")
    public List<T> list() {
        return session.createQuery("from " + this.classe.getName()).list();
    }

    @SuppressWarnings("unchecked")
    public T getById(Long id) {
        Query q = session.createQuery("from " + this.classe.getName() + " where id = :id");
        q.setParameter("id", id);
        return (T) q.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<T> getList(List<Long> ids) {
        if (ids != null && ids.size() > 0) {
            Query q = session.createQuery("from " + this.classe.getName() + " where id in (:ids)");
            q.setParameterList("ids", ids);
            return q.list();
        } else {
            return Collections.emptyList();
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> listSorted(String... listFields) {
        String q = "from " + this.classe.getName();
        if (listFields.length > 0) q += " order by " + join(listFields, ", ");
        return session.createQuery(q).list();
    }
    public int countAll() {
        return ConvertUtils.convertTo(this.session.createQuery("select count(*) from " + this.classe.getName()).uniqueResult(), Integer.class);
    }
    //</editor-fold>

    //<editor-fold desc="[Update]">
    @SuppressWarnings("unchecked")
    public void update(T entity, A changer) {
        try {
            T oldEntity = (T) session.get(this.classe, entity.getId());
            if (entity instanceof IAuditable) {
                if (oldEntity != null && ((IAuditable<A>)oldEntity).getCreatedAt() != null) {
                    ((IAuditable<A>)entity).setCreatedAt(((IAuditable<A>) oldEntity).getCreatedAt());
                }
                if (oldEntity != null && ((IAuditable<A>)oldEntity).getCreatedBy() != null) {
                    ((IAuditable<A>)entity).setCreatedBy(((IAuditable<A>) oldEntity).getCreatedBy());
                }
                ((IAuditable<A>)entity).setChangedAt(new LocalDateTime());
                ((IAuditable<A>)entity).setChangedBy(changer);
            }
            session.getTransaction().begin();
            session.merge(entity);
            session.getTransaction().commit();
            logger.info("Objeto [" + this.getEntityName() + "] atualizado com id = " + String.valueOf(entity.getId()));
        } catch (Exception e) {
            session.getTransaction().rollback();
            logger.info("Erro ao atualizar objeto. " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void forceUpdate(T entity) {
        try {
            session.getTransaction().begin();
            session.update(entity);
            session.getTransaction().commit();
            logger.info("Objeto [" + this.getEntityName() + "] atualizado com id = " + String.valueOf(entity.getId()));
        } catch (Exception e) {
            session.getTransaction().rollback();
            logger.info("Erro ao atualizar objeto. " + e.getMessage());
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
        } catch (Exception e) {
            session.getTransaction().rollback();
            logger.info("Erro ao remover objeto. " + e.getMessage());
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
