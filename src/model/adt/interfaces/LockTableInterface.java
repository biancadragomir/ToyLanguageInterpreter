package model.adt.interfaces;

import java.util.Collection;
import java.util.Map;

public interface LockTableInterface<T,E> {
    boolean contains(T k);
    E get(T k);
    void add(T k, E v);
    void update(T k, E n);
    Iterable<T> getAll();
    Iterable<E> getValues();
    Map<T,E> getContent();
    MyDictionaryInterface<T,E> copy();
    String toString();
    Collection<E> getCollection();
}
