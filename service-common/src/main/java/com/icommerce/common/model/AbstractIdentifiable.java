package com.icommerce.common.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractIdentifiable<T extends AbstractIdentifiable<T>> implements Serializable, Identifiable<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @Override
    public int hashCode() {
        return Objects.hash(id, deepHashCode());
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        T other = (T) obj;
        if (this.id != other.getId()) {
            return false;
        }

        return deepEquals(other);
    }

}