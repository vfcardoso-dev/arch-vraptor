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
    private List<HighchartsBarColumnSeries> series;
    private List<String> categories = new ArrayList<>(0);

    public HighchartsBarColumnMultiSeriesJsonReturn() {
    }

    public HighchartsBarColumnMultiSeriesJsonReturn(String title, String label, List<HighchartsBarColumnSeries> series, List<String> categories) {
        this.title = title;
        this.label = label;
        this.series = series;
        this.categories = categories;
    }

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

    public List<HighchartsBarColumnSeries> getSeries() {
        return series;
    }

    public void setSeries(List<HighchartsBarColumnSeries> series) {
        this.series = series;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
