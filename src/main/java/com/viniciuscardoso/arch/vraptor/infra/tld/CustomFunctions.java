package com.viniciuscardoso.arch.vraptor.infra.tld;

import java.util.List;
import java.util.Set;

/**
 * @author Vinícius Cardoso
 * @project trf-main
 */
public class CustomFunctions {

    public static boolean containsList(List<Object> lista, Object o) {
        if (lista != null && o != null) {
            return lista.contains(o);
        } else {
            return false;
        }
    }

    public static boolean containsSet(Set<Object> lista, Object o) {
        if (lista != null && o != null) {
            return lista.contains(o);
        } else {
            return false;
        }
    }
}
