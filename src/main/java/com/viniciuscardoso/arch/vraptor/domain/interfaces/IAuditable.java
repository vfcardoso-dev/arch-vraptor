package com.viniciuscardoso.arch.vraptor.domain.interfaces;

import com.viniciuscardoso.arch.vraptor.domain.common.AbstractEntity;
import org.joda.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 * User: Vinícius
 * Date: 21/07/14
 * Time: 15:20
 * To change this template use File | Settings | File Templates.
 */
public interface IAuditable<T extends IActorAudited> {

    public abstract LocalDateTime getCreatedAt();
    public abstract void setCreatedAt(LocalDateTime creationDate);
    public abstract T getCreatedBy();
    public abstract void setCreatedBy(T creator);
    public abstract LocalDateTime getChangedAt();
    public abstract void setChangedAt(LocalDateTime changeDate);
    public abstract T getChangedBy();
    public abstract void setChangedBy(T changer);
}
