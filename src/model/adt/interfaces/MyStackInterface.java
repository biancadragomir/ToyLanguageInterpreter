package model.adt.interfaces;

import java.util.Collection;

public interface MyStackInterface<T> {
    void push(T e);
    T pop();
    boolean isEmpty();
    Iterable<T> getAll();
    String toString();
    Collection<T> getCollection();
}