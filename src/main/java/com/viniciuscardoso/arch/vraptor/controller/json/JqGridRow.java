package com.viniciuscardoso.arch.vraptor.controller.json;

/**
 * Project: arch
 * User: Vinicius Cardoso
 * Date: 20/05/14
 * Time: 16:38
 */
public class JqGridRow {
    private String id;
    private Object[] cell;

    public JqGridRow(String id, Object[] cell) {
        this.id = id;
        this.cell = cell;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object[] getCell() {
        return cell;
    }

    public void setCell(Object[] cell) {
        this.cell = cell;
    }

    public int getIdInteger() {
        return Integer.parseInt(this.id);
    }
}
