package com.viniciuscardoso.arch.vraptor.controller.interfaces;


import br.com.caelum.vraptor.Get;
import com.viniciuscardoso.arch.vraptor.domain.common.AbstractEntity;

/**
 * Project: arch
 * User: Vinicius Cardoso
 * Date: 28/03/14
 * Time: 14:38
 */

public interface IControllerExternal<E extends AbstractEntity> {

    @Get
    public abstract void list();
}
