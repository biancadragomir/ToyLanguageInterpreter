package model.adt;

import model.adt.interfaces.MyHeapInterface;

import java.util.HashMap;
import java.util.Map;

public class MyHeap<K,V> implements MyHeapInterface<K,V> {
    private Map<K, V> map = new HashMap<>();

    public Map<K, V> getMap() {
        return map;
    }

    @Override
    public void add(K k, V v) {
        map.put(k, v);
    }

    @Override
    public Iterable<K> getAll() {
        return map.keySet();
    }

    @Override
    public Iterable<V> getValues() {
        return map.values();
    }

    @Override
    public V get(K k) {
        return map.get(k);
    }

    @Override
    public void delete(K v) {
        map.remove(v);
    }

    @Override
    public void update(K k, V v) {
        map.put(k,v);
    }
    public boolean contains(K k){
        return map.containsKey(k);
    }

    public void setMap(Map<K, V> map) {
        this.map = map;
    }
    public void setContent(Map<K, V> newC){
        map = newC;
    }
    public Map<K,V> getContent(){
        return this.map;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<K,V> e : map.entrySet()){
            sb.append('\n');
            sb.append(e.getKey());
            sb.append("-->");
            sb.append(e.getValue());
        }
        return sb.toString();
    }

}