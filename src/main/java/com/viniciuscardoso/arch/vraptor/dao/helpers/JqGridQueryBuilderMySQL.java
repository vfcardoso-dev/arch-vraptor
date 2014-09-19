package com.viniciuscardoso.arch.vraptor.dao.helpers;

import br.com.caelum.vraptor.ioc.Component;
import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

/**
 * Project: trf-main
 * User: Vinicius Cardoso
 * Date: 10/07/14
 * Time: 11:10
 */
@Component
public class JqGridQueryBuilderMySQL extends JqGridQueryBuilder implements IJqGridQueryBuilder {

    @Override
    public void setQueryJqGridFilter(StringBuilder consulta, String sField, String sOper, String sidx, String sord) {
        if (!consulta.toString().toLowerCase().contains(" where ")) {
            consulta.append(" WHERE 1=1 ");
        }

        if (sField != null && sOper != null) {
            consulta.append("having ").append(sField).append(convertSearchOper(sOper)).append(" :param ");
        }

        if (sidx != null || sord != null) {
            consulta.append("order by ").append(setOrderBy(sidx, sord));
        }
    }

    @Override
    public String setOrderBy(String sidx, String sord) {
        return super.setOrderBy(sidx,sord);
    }

    @Override
    public String convertSearchOper(String sOper) {
        return super.convertSearchOper(sOper);
    }

    @Override
    public void addCustomParametersToQuery(Query q, String sField, String sString, String sOper) {
        super.addCustomParametersToQuery(q, sField, sString, sOper);
    }

    @Override
    public List<Object[]> executeQuery(Query q) {
        return super.executeQuery(q);
    }
}
