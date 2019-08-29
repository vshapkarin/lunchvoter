package ru.lunchvoter.to;

import ru.lunchvoter.HasId;

public class AbstractTo implements HasId<Integer> {
    protected Integer id;

    public AbstractTo() {
    }

    public AbstractTo(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
