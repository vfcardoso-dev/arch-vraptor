package com.viniciuscardoso.arch.vraptor.controller.common;

import br.com.caelum.vraptor.Result;
import com.viniciuscardoso.arch.vraptor.dao.common.AbstractGenericDao;
import com.viniciuscardoso.arch.vraptor.domain.common.AbstractEntity;

/**
 * @author Vinícius Cardoso
 * @project GDR
 */
public abstract class AbstractExternalController<T extends AbstractGenericDao, E extends AbstractEntity> {

    private Result result;
    private T dao;

    public AbstractExternalController(Result result, T dao) {
        this.result = result;
        this.dao = dao;
    }

    /**
     * Lista as entidades baseado nos campos passados e inclui no result
     * @param nameList nome a ser definido no result para conter o retorno
     * @param fieldOrder array contendo campos para ordenar
     * @throws Exception 
     */
    public void list(String nameList, String[] fieldOrder) {
        result.include(nameList, dao.listSorted(fieldOrder));
    }
}
