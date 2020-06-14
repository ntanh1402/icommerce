package com.icommerce.common.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractTimeBaseIdentifiable<T extends AbstractTimeBaseIdentifiable<T>> extends
        AbstractIdentifiable<T> {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime default CURRENT_TIMESTAMP")
    protected Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    protected Date lastModifiedTime;

    @PrePersist
    protected void onCreate() {
        createdTime = new Date();
        lastModifiedTime = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        lastModifiedTime = new Date();
    }

}