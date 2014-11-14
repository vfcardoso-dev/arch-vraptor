package com.viniciuscardoso.arch.vraptor.controller.interfaces;


import com.viniciuscardoso.arch.vraptor.domain.common.AbstractEntity;

/**
 * Project: arch
 * User: Vinicius Cardoso
 * Date: 28/03/14
 * Time: 14:38
 */

public interface IControllerCrud<E extends AbstractEntity> {

    public abstract void list();

    public abstract void create();

    public abstract void add(E entity);

    public abstract void edit(Long id);

    public abstract void update(E entity);

    public abstract void remove(Long id);
}
