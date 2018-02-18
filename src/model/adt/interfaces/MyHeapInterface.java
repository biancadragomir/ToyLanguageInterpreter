package model.adt.interfaces;

import java.util.Map;

public interface MyHeapInterface <K,V> {
    void add(K k, V v);
    V get(K k);
    void update(K k, V v);
    void delete(K v);
    Iterable<K> getAll();
    Iterable<V> getValues();
    boolean contains(K k);
    void setMap(Map<K, V> map);
    void setContent(Map<K, V> newC);
    Map<K,V> getContent();
    String toString();
    Map<K, V> getMap();
}