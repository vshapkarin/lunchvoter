package ru.lunchvoter.model;

import org.hibernate.Hibernate;
import ru.lunchvoter.HasId;

import javax.persistence.*;

@MappedSuperclass
@Access(value = AccessType.FIELD)
public class AbstractEntity<T> implements HasId<T> {

    @Id
    @GeneratedValue(generator = "id_generator", strategy = GenerationType.IDENTITY)
    protected T id;

    protected AbstractEntity() {
    }

    protected AbstractEntity(T id) {
        this.id = id;
    }

    @Override
    public T getId() {
        return id;
    }

    @Override
    public void setId(T id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(Hibernate.getClass(o))) {
            return false;
        }
        AbstractEntity that = (AbstractEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}
