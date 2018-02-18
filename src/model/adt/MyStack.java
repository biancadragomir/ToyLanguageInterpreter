package model.adt;

import model.adt.interfaces.MyStackInterface;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

public class MyStack<T> implements MyStackInterface<T> {
    private Deque<T> stack = new ArrayDeque<>();
    public void push(T e){
        stack.push(e);
    }
    public boolean isEmpty(){
        return stack.isEmpty();
    }
    public T pop(){
        return stack.pop();
    }
    public Iterable<T> getAll(){
        return stack;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (T t : stack) {
            sb.append('\n');
            sb.append(t);
        }
        return sb.toString();
    }

    public Collection<T> getCollection() {
        return stack;
    }


}