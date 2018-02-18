package model.adt;

import model.adt.interfaces.MyListInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyList<T> implements MyListInterface<T> {
    private List<T> list = new ArrayList<>(10);
    public void add(T e){
        list.add(e);
    }
    public String toString(){
        StringBuffer sb = new StringBuffer();
        for(T e: list){
            sb.append(e);
            sb.append("\n");
        }
        return sb.toString();
    }
    public Iterable<T> getAll(){
        return list;
    }
    public Collection<T> getCollection(){
        return list;
    }

}