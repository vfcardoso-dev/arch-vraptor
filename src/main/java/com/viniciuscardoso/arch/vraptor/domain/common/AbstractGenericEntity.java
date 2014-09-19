package com.viniciuscardoso.arch.vraptor.domain.common;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Project: arch
 * User: Vinicius Cardoso
 * Date: 28/03/14
 * Time: 14:38
 */

@MappedSuperclass
public abstract class AbstractGenericEntity implements Serializable {

    private static final long serialVersionUID = 2L;

    //Serializable members
    @Override
    public abstract int hashCode();
    @Override
    public abstract boolean equals(Object obj);

}
