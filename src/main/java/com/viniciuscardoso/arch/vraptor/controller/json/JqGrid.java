package com.viniciuscardoso.arch.vraptor.controller.json;

import java.util.ArrayList;

/**
 * Project: arch
 * User: Vinicius Cardoso
 * Date: 20/05/14
 * Time: 16:38
 */
public class JqGrid {
    private String page;
    private String records;
    private ArrayList<JqGridRow> rows = new ArrayList<JqGridRow>(0);
    private int total;
    private Object userData;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRecords() {
        return records;
    }

    public void setRecords(String records) {
        this.records = records;
    }

    public ArrayList<JqGridRow> getRows() {
        return rows;
    }

    public void setRows(ArrayList<JqGridRow> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Object getUserData() {
        return userData;
    }

    public void setUserData(Object userData) {
        this.userData = userData;
    }
}
