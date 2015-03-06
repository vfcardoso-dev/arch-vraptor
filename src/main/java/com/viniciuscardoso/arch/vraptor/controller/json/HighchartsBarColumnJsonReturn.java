package com.viniciuscardoso.arch.vraptor.controller.json;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: logplan
 * User: Vinicius Cardoso
 * Date: 13/12/13
 * Time: 09:01
 */
public class HighchartsBarColumnJsonReturn {
    private String name;
    private List<Integer> values = new ArrayList<>(0);
    private List<String> categories = new ArrayList<>(0);

    public void setValues(List<Integer> values) {
        this.values = values;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getCategories() {
        return categories;
    }
}
