package model.adt.interfaces;

import java.util.Collection;

public interface MyListInterface<T> {
    void add(T e);
    Iterable<T> getAll();
    Collection<T> getCollection();
    String toString();
}