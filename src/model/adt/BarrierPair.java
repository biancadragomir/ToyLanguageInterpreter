package model.adt;

public class BarrierPair<T,E> {

    public BarrierPair(T t, E e){
        first = t; second = e;
    }
    private T first; private E second;

    public E getSecond() {
        return second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public void setSecond(E second) {
        this.second = second;
    }

    public String toString(){
        return " <["+ first.toString()+ "], " + second+">";
    }
}
