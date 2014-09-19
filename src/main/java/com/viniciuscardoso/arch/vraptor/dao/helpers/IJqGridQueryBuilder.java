package com.viniciuscardoso.arch.vraptor.dao.helpers;

import org.hibernate.Query;

import java.util.List;

/**
 * User: transforma-siblend
 * User: Vinícius
 * Date: 06/08/14
 * Time: 14:17
 */
public interface IJqGridQueryBuilder {

    public void setQueryJqGridFilter(StringBuilder consulta, String sField, String sOper, String sidx, String sord);

    public String setOrderBy(String sidx, String sord);

    public String convertSearchOper(String sOper);

    public void addCustomParametersToQuery(Query q, String sField, String sString, String sOper);

    public List<Object[]> executeQuery(Query q);
}
