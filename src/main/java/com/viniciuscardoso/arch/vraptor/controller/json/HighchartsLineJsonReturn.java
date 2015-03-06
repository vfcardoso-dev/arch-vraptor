package com.viniciuscardoso.arch.vraptor.controller.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project: arch-vraptor
 * User: Vinícius
 * Date: 05/03/2015
 * Time: 15:35
 */
public class HighchartsLineJsonReturn {
    private String title;
    private String label;
    private Map<String, List<Double>> mapa = new HashMap<>();

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

    public Map<String, List<Double>> getMapa() {
        return mapa;
    }

    public void setMapa(Map<String, List<Double>> mapa) {
        this.mapa = mapa;
    }
}
