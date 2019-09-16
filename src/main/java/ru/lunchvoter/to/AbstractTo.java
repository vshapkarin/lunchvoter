package ru.lunchvoter.to;

import ru.lunchvoter.HasId;

public class AbstractTo<T> implements HasId<T> {
    protected T id;

    public AbstractTo() {
    }

    public AbstractTo(T id) {
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
}
