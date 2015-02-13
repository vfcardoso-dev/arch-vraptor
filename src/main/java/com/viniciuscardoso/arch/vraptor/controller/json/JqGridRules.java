package com.viniciuscardoso.arch.vraptor.controller.json;

/**
 * Project: logplan-v1
 * User: Vinícius
 * Date: 10/02/2015
 * Time: 15:31
 */
public class JqGridRules {

    private String field;
    private String op;
    private String data;

    public JqGridRules(String field, String op, String data) {
        this.field = field;
        this.op = op;
        this.data = data;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
