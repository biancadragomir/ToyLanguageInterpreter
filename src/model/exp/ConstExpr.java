package model.exp;

import model.adt.interfaces.MyDictionaryInterface;
import model.adt.interfaces.MyHeapInterface;

public class ConstExpr implements Exp {
    private int val;
    public ConstExpr(int a){
        val = a;
    }
    public int eval(MyDictionaryInterface<String, Integer> st, MyHeapInterface<Integer, Integer> h){
        return val;
    }
    @Override
    public String toString(){
        return ""+val;
    }
}