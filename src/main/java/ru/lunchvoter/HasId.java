package ru.lunchvoter;

public interface HasId<T> {
    T getId();

    void setId(T id);

    default boolean isNew() {
        return getId() == null;
    }
}
