package com.viniciuscardoso.arch.vraptor.controller.common;


import br.com.caelum.vraptor.Result;
import com.viniciuscardoso.arch.vraptor.controller.interfaces.IControllerCrud;
import com.viniciuscardoso.arch.vraptor.controller.interfaces.IControllerCrudUpload;
import com.viniciuscardoso.arch.vraptor.dao.common.AbstractAuditableDao;
import com.viniciuscardoso.arch.vraptor.domain.common.AbstractEntity;
import com.viniciuscardoso.arch.vraptor.domain.interfaces.IActorAudited;
import com.viniciuscardoso.arch.vraptor.exception.ControllerException;
import com.viniciuscardoso.arch.vraptor.utility.ControllerUtils;

/**
 * Project: arch
 * User: Vinicius Cardoso
 * Date: 28/03/14
 * Time: 14:38
 */

@SuppressWarnings("unchecked")
public abstract class AbstractAuditableController<T extends AbstractAuditableDao, E extends AbstractEntity> {

    private Result result;
    private T dao;

    public AbstractAuditableController(Result result, T dao) {
        this.result = result;
        this.dao = dao;
    }

    /**
     * Lista as entidades baseado nos campos passados e inclui no result
     * @param nameList nome a ser definido no result para conter o retorno
     * @param fieldOrder array contendo campos para ordenar
     */
    public void list(String nameList, String[] fieldOrder) {
        result.include(nameList, dao.listSorted(fieldOrder));
    }

    /**
     * Retorna um formulário novo de entidade no result
     */
    public void createObject() {
    }


    /**
     * Adiciona uma nova entidade persistida no banco
     * @param entity entidade a ser persistida
     * @param textSuccess texto a ser apresentado (substantivo + verbo) para quando persistir com sucesso
     * @param self objeto controller instanciado, para fazer o redirecionamento após salvar
     */
    public void add(E entity, String textSuccess, IControllerCrud self, IActorAudited creator) {
        try {
            dao.save(entity, creator);
            ControllerUtils.defineSuccessMessage(result, textSuccess);
            result.redirectTo(self).list();
        } catch (ControllerException e) {
            ControllerUtils.defineErrorMessage(result, e);
            result.redirectTo(self).create();
        }

    }
    
    /**
     * Adiciona uma nova entidade persistida no banco, com upload
     * @param entity entidade a ser persistida
     * @param textSuccess texto a ser apresentado (substantivo + verbo) para quando persistir com sucesso
     * @param self objeto controller instanciado, para fazer o redirecionamento após salvar
     */
    public void add(E entity, String textSuccess, IControllerCrudUpload self, IActorAudited creator) {
        try {
            dao.save(entity, creator);
            ControllerUtils.defineSuccessMessage(result, textSuccess);
            result.redirectTo(self).list();
        } catch (ControllerException e) {
            ControllerUtils.defineErrorMessage(result, e);
            result.redirectTo(self).create();
        }

    }

    /**
     * Mostra um formulário de alteração com a entidade a ser alterada já preenchida
     * @param id id da entidade
     * @param nameInResult nome do retorno no result
     */
    public void edit(Long id, String nameInResult) {
        result.include(nameInResult, (E) dao.getById(id));
    }

    /**
     * Atualiza uma entidade persistida no banco
     * @param entity entidade a ser persistida
     * @param textSuccess texto a ser apresentado (substantivo + verbo) para quando persistir com sucesso
     * @param self objeto controller instanciado, para fazer o redirecionamento após atualizar
     */
    public void update(E entity, String textSuccess, IControllerCrud self, IActorAudited changer) {
        try {
            dao.update(entity, changer);
            ControllerUtils.defineSuccessMessage(result, textSuccess);
            result.redirectTo(self).list();
        } catch (ControllerException e) {
            ControllerUtils.defineErrorMessage(result, e);
            result.redirectTo(self).edit(entity.getId());
        }
    }
    
    /**
     * Atualiza uma entidade persistida no banco, com upload
     * @param entity entidade a ser persistida
     * @param textSuccess texto a ser apresentado (substantivo + verbo) para quando persistir com sucesso
     * @param self objeto controller instanciado, para fazer o redirecionamento após atualizar
     */
    public void update(E entity, String textSuccess, IControllerCrudUpload self, IActorAudited changer) {
        try {
            dao.update(entity, changer);
            ControllerUtils.defineSuccessMessage(result, textSuccess);
            result.redirectTo(self).list();
        } catch (ControllerException e) {
            ControllerUtils.defineErrorMessage(result, e);
            result.redirectTo(self).edit(entity.getId());
        }
    }

    /**
     * Remove uma entidade do banco
     * @param id id da entidade a ser removida
     * @param textSuccess texto a ser apresentado (substantivo + verbo) para quando remover com sucesso
     * @param self objeto controller instanciado, para fazer o redirecionamento após remover
     */
    public void remove(Long id, String textSuccess, IControllerCrud self) {
        try {
            E e = (E) dao.getById(id);
            dao.remove(e);
            ControllerUtils.defineSuccessMessage(result, textSuccess);
        } catch (ControllerException e) {
            ControllerUtils.defineErrorMessage(result, e);
        } finally {
            result.redirectTo(self).list();
        }
    }
    
    /**
     * Remove uma entidade do banco, com upload
     * @param id id da entidade a ser removida
     * @param textSuccess texto a ser apresentado (substantivo + verbo) para quando remover com sucesso
     * @param self objeto controller instanciado, para fazer o redirecionamento após remover

     */
    public void remove(Long id, String textSuccess, IControllerCrudUpload self) {
        try {
            E e = (E) dao.getById(id);
            dao.remove(e);
            ControllerUtils.defineSuccessMessage(result, textSuccess);
        } catch (ControllerException e) {
            ControllerUtils.defineErrorMessage(result, e);
        } finally {
            result.redirectTo(self).list();
        }

    }
}
