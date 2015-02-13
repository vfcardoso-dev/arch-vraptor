package com.viniciuscardoso.arch.vraptor.dao.helpers;

import br.com.caelum.vraptor.ioc.Component;
import com.viniciuscardoso.arch.vraptor.controller.json.JqGridFilters;
import com.viniciuscardoso.arch.vraptor.controller.json.JqGridRules;
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

    public void setQueryJqGridFilter(StringBuilder consulta, String sField, String sOper, String sidx, String sord, JqGridFilters filters) {
        if (!consulta.toString().toLowerCase().contains(" where ")) {
            consulta.append(" WHERE 1=1 ");
        }
        if (sField != null && sOper != null) {
            consulta.append("and ").append(sField).append(this.convertSearchOper(sOper)).append(" :param ");
        } else if (filters != null) {
            JqGridRules rule;
            consulta.append("and ");
            for (int i = 0; i < filters.getRules().size(); i++) {
                rule = filters.getRules().get(i);
                consulta.append(rule.getField())
                        .append(convertSearchOper(rule.getOp()))
                        .append(" :param").append(String.valueOf(i)).append(" ");
                if (i < filters.getRules().size() - 1) {
                    consulta.append(" ").append(filters.getGroupOp()).append(" ");
                }
            }
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

    public void addCustomParametersToQuery(Query q, String sField, String sString, String sOper, JqGridFilters filters) {
        if (sField != null && sOper != null) {
            if ("cn".equals(sOper)) {
                q.setParameter("param", sString);
            } else {
                super.addCustomParametersToQuery(q, sField, sString, sOper);
            }
        } else if (filters != null) {
            JqGridRules rule;
            for (int i = 0; i < filters.getRules().size(); i++) {
                rule = filters.getRules().get(i);
                String paramIdx = "param" + String.valueOf(i);
                if ("cn".equals(rule.getOp())) {
                    q.setParameter(paramIdx, rule.getData());
                } else {
                    super.addCustomParametersToQuery(q, rule.getField(), rule.getData(), rule.getOp());
                }
            }

        }
    }

    public List<Object[]> executeQuery(Query q) {
        return super.executeQuery(q);
    }
}
