package com.viniciuscardoso.arch.vraptor.dao.helpers;

import com.viniciuscardoso.arch.vraptor.controller.json.JqGridFilters;
import com.viniciuscardoso.arch.vraptor.controller.json.JqGridRules;
import com.viniciuscardoso.arch.vraptor.utility.ValidationUtils;
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
public abstract class JqGridQueryBuilder {

    protected abstract void setQueryJqGridFilter(StringBuilder consulta, String sField, String sOper, String sidx, String sord);

    protected abstract void setQueryJqGridFilter(StringBuilder consulta, String sField, String sOper, String sidx, String sord, JqGridFilters filters);

    protected String setOrderBy(String sidx, String sord) {
        if (sidx.charAt(sidx.length() - 1) == ',') {
            return (String) sidx.subSequence(0, sidx.length() - 1);
        } else {
            return sidx + " " + sord + " ";
        }
    }

    protected String convertSearchOper(String sOper) {
        //['eq','ne','lt','le','gt','ge', 'cn', 'nc', 'nu', 'nn']
        if ("eq".equals(sOper)) {
            return " = ";
        } else if ("lt".equals(sOper)) {
            return " < ";
        } else if ("le".equals(sOper)) {
            return " <= ";
        } else if ("gt".equals(sOper)) {
            return " > ";
        } else if ("ge".equals(sOper)) {
            return " >= ";
        } else if ("cn".equals(sOper)) {
            return " like ";
        } else {
            return null;
        }
    }

    protected void addCustomParametersToQuery(Query q, String sField, String sString, String sOper) {
        if (sField != null && sOper != null) {
            if (ValidationUtils.containsIgnoreCase(sField,"ROWID")) {
                q.setParameter("param", Long.valueOf(sString));
            } else if (ValidationUtils.containsIgnoreCase(sField, "DT")) {
                final DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
                final LocalDate dt = dtf.parseDateTime(sString).toLocalDate();
                q.setParameter("param", dt);
            } else if (ValidationUtils.containsIgnoreCase(sField,"DT_TM")) {
                final DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
                final LocalDateTime dttm = dtf.parseDateTime(sString).toLocalDateTime();
                q.setParameter("param", dttm);
            } else if ("cn".equals(sOper)) {
                q.setParameter("param", "%" + sString + "%");
            } else {
                q.setParameter("param", sString);
            }
        }
    }

    protected void addCustomParametersToQuery(Query q, String sField, String sString, String sOper, JqGridFilters filters) {
        if (sField != null && sOper != null) {
            if (ValidationUtils.containsIgnoreCase(sField,"ROWID")) {
                q.setParameter("param", Long.valueOf(sString));
            } else if (ValidationUtils.containsIgnoreCase(sField, "DT")) {
                final DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
                final LocalDate dt = dtf.parseDateTime(sString).toLocalDate();
                q.setParameter("param", dt);
            } else if (ValidationUtils.containsIgnoreCase(sField, "DT_TM")) {
                final DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
                final LocalDateTime dttm = dtf.parseDateTime(sString).toLocalDateTime();
                q.setParameter("param", dttm);
            } else if ("cn".equals(sOper)) {
                q.setParameter("param", "%" + sString + "%");
            } else {
                q.setParameter("param", sString);
            }
        } else if (filters != null) {
            JqGridRules rule;
            for(int i = 0; i < filters.getRules().size(); i++) {
                rule = filters.getRules().get(i);
                String paramIdx = "param" + String.valueOf(i);
                if (ValidationUtils.containsIgnoreCase(rule.getField(), "ROWID")) {
                    q.setParameter(paramIdx, Long.valueOf(rule.getData()));
                } else if (ValidationUtils.containsIgnoreCase(rule.getField(), "DT")) {
                    final DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
                    final LocalDate dt = dtf.parseDateTime(rule.getData()).toLocalDate();
                    q.setParameter(paramIdx, dt);
                } else if (ValidationUtils.containsIgnoreCase(rule.getField(),"DT_TM")) {
                    final DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
                    final LocalDateTime dttm = dtf.parseDateTime(rule.getData()).toLocalDateTime();
                    q.setParameter(paramIdx, dttm);
                } else if ("cn".equals(rule.getOp())) {
                    q.setParameter(paramIdx, "%" + rule.getData() + "%");
                } else {
                    q.setParameter(paramIdx, rule.getData());
                }
            }
        }
    }

    protected List<Object[]> executeQuery(Query q) {
        try {
            q.setCacheMode(CacheMode.REFRESH);
            return q.list();
        } catch (Exception e) {
            throw e;
        }
    }
}
