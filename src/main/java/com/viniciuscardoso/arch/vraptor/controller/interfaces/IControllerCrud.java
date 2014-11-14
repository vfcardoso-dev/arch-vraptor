package com.viniciuscardoso.arch.vraptor.controller.interfaces;


import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import com.viniciuscardoso.arch.vraptor.domain.common.AbstractEntity;

/**
 * Project: arch
 * User: Vinicius Cardoso
 * Date: 28/03/14
 * Time: 14:38
 */

public interface IControllerCrud<E extends AbstractEntity> {

    @Get
    public abstract void list();

    @Get
    public abstract void create();

    @Post
    public abstract void add(E entity);

    @Get
    public abstract void edit(Long id);

    @Post
    public abstract void update(E entity);

    @Get
    public abstract void remove(Long id);
}
