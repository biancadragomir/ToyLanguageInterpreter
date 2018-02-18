package model.adt;

import model.adt.interfaces.FileTableInterface;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FileTable<K,V> implements FileTableInterface<K, V> {
    private HashMap<K, V> dict; // so K will be an integer and V a file data (filename = string and file descriptor = buffered reader)

    public FileTable(){
        dict  = new HashMap<>();
    }

    public boolean contains(K k){
        return dict.containsKey(k);
    }

    public void add(K k, V v){
        dict.put(k, v);

    }
    public void remove(K k){
        dict.remove(k);
    }

    public V get(K k){
        return dict.get(k);
    }

    @Override
    public Iterable<K> getAll() {
        return dict.keySet();
    }

    public Iterable<V> getValues(){
        return dict.values();
    }

    public Map<K, V> getContent(){
        return dict;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<K, V> e : dict.entrySet()) {
            sb.append('\n');
            sb.append(e.getKey());
            sb.append("-->");
            sb.append(e.getValue());
        }
        return sb.toString();
    }

    public Collection<V> getCollection() {
        return dict.values();
    }
}
