package com.viniciuscardoso.arch.vraptor.controller.json;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: logplan
 * User: Vinicius Cardoso
 * Date: 13/12/13
 * Time: 09:01
 */
public class HighchartsBarColumnMultiSeriesJsonReturn {
    private String title;
    private String label;
    private HighchartsBarColumnSeries series;
    private List<String> categories = new ArrayList<>(0);

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public HighchartsBarColumnSeries getSeries() {
        return series;
    }

    public void setSeries(HighchartsBarColumnSeries series) {
        this.series = series;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
