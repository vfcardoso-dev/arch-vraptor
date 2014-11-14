/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viniciuscardoso.arch.vraptor.controller.interfaces;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import com.viniciuscardoso.arch.vraptor.domain.common.AbstractEntity;

/**
 * Project: arch
 * User: Vinicius Cardoso
 * Date: 28/03/14
 * Time: 14:38
 */

public interface IControllerCrudUpload<E extends AbstractEntity> {

    @Get
    public abstract void list();

    @Get
    public abstract void create();

    @Post
    public abstract void add(E entity, UploadedFile arq);

    @Get
    public abstract void edit(Long id);

    @Post
    public abstract void update(E entity, UploadedFile arq);

    @Get
    public abstract void remove(Long id);
    
}
