package com.viniciuscardoso.arch.vraptor.controller.json;

/**
 * Project: logplan
 * User: Vinicius Cardoso
 * Date: 13/12/13
 * Time: 10:52
 */
public class HtmlSelectOptionJson {
    private String key;
    private String value;

    public HtmlSelectOptionJson() {
    }

    public HtmlSelectOptionJson(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
