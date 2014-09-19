package com.viniciuscardoso.arch.vraptor.dao.helpers;

import br.com.caelum.vraptor.ioc.Component;
import org.hibernate.Query;

import java.util.List;

/**
 * Project: trf-main
 * User: Vinicius Cardoso
 * Date: 10/07/14
 * Time: 11:10
 */

@Component
public class JqGridQueryBuilderFirebird extends JqGridQueryBuilder implements IJqGridQueryBuilder {

    public void setQueryJqGridFilter(StringBuilder consulta, String sField, String sOper, String sidx, String sord) {
        if (!consulta.toString().toLowerCase().contains(" where ")) {
            consulta.append(" WHERE 1=1 ");
        }
        if (sField != null && sOper != null) {
            consulta.append("and ").append(sField).append(this.convertSearchOper(sOper)).append(" :param ");
        }
        if (sidx != null || sord != null) {
            consulta.append("order by ").append(setOrderBy(sidx, sord));
        }
    }

    public String setOrderBy(String sidx, String sord) {
        return super.setOrderBy(sidx, sord);
    }

    public String convertSearchOper(String sOper) {
        if ("cn".equals(sOper)) {
            return " containing ";
        } else {
            return super.convertSearchOper(sOper);
        }
    }

    public void addCustomParametersToQuery(Query q, String sField, String sString, String sOper) {
        if (sField != null && sOper != null) {
             if ("cn".equals(sOper)) {
                q.setParameter("param", sString);
            } else {
                super.addCustomParametersToQuery(q, sField, sString, sOper);
            }
        }
    }

    public List<Object[]> executeQuery(Query q) {
        return super.executeQuery(q);
    }
}
