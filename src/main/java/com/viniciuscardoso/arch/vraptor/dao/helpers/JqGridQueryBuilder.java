package com.viniciuscardoso.arch.vraptor.dao.helpers;

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

    protected void setQueryJqGridFilter(StringBuilder consulta, String sField, String sOper, String sidx, String sord) {
        if (!consulta.toString().toLowerCase().contains(" where ")) {
            consulta.append(" WHERE 1=1 ");
        }

        if (sField != null && sOper != null) {
            //consulta.append("having ").append(sField).append(convertSearchOper(sOper)).append(" :param ");
            //MS SQL não suporta 'having' para querys sem 'group by' como o MYSQL.
            consulta.append("and ").append(sField).append(convertSearchOper(sOper)).append(" :param ");
        }

        if (sidx != null || sord != null) {
            consulta.append("order by ").append(setOrderBy(sidx, sord));
        }
    }

//    public static void setQueryJqGridFilterFirebird(StringBuilder consulta, String sField, String sOper, String sidx, String sord) {
//        if (!consulta.toString().toLowerCase().contains(" where ")) {
//            consulta.append(" WHERE 1=1 ");
//        }
//
//        if (sField != null && sOper != null) {
//            //consulta.append("having ").append(sField).append(convertSearchOper(sOper)).append(" :param ");
//            //MS SQL não suporta 'having' para querys sem 'group by' como o MYSQL.
//            consulta.append("and ").append(sField).append(convertSearchOperFirebird(sOper)).append(" :param ");
//        }
//
//        if (sidx != null || sord != null) {
//            consulta.append("order by ").append(setOrderBy(sidx, sord));
//        }
//    }

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
            if (sField.contains("ROWID")) {
                q.setParameter("param", Long.valueOf(sString));
            } else if (sField.contains("DT")) {
                final DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
                final LocalDate dt = dtf.parseDateTime(sString).toLocalDate();
                q.setParameter("param", dt);
            } else if (sField.contains("DT_TM")) {
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

    protected List<Object[]> executeQuery(Query q) {
        try {
            q.setCacheMode(CacheMode.REFRESH);
            return q.list();
        } catch (Exception e) {
            throw e;
        }
    }
}
