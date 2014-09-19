package com.viniciuscardoso.arch.vraptor.controller.json;

/**
 * Project: logplan
 * User: Vinicius Cardoso
 * Date: 13/12/13
 * Time: 10:52
 */
public class HtmlSelectOptionJsonReturn {
    private Long id;
    private String description;

    public HtmlSelectOptionJsonReturn() {
    }

    public HtmlSelectOptionJsonReturn(Long id, String description) {
        this.description = description;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
