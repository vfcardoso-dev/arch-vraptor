package com.viniciuscardoso.arch.vraptor.controller.json;

import java.util.List;

/**
 * Project: logplan-v1
 * User: Vinícius
 * Date: 10/02/2015
 * Time: 15:31
 */
public class JqGridFilters {
    private String groupOp;
    private List<JqGridRules> rules;

    public JqGridFilters(String groupOp, List<JqGridRules> rules) {
        this.groupOp = groupOp;
        this.rules = rules;
    }

    public String getGroupOp() {
        return groupOp;
    }

    public void setGroupOp(String groupOp) {
        this.groupOp = groupOp;
    }

    public List<JqGridRules> getRules() {
        return rules;
    }

    public void setRules(List<JqGridRules> rules) {
        this.rules = rules;
    }
}
