package model.adt.interfaces;

import java.util.Collection;
import java.util.Map;

public interface FileTableInterface<E,V> {
    void add(E e, V v);
    void remove(E e);
    V get(E e);
    boolean contains(E e);
    Iterable<E> getAll();
    Iterable<V> getValues();
    Map<E, V> getContent();
    String toString();
    Collection<V> getCollection();

}
