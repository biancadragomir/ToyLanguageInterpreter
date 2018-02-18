package model.exp;

import model.adt.interfaces.MyDictionaryInterface;
import model.adt.interfaces.MyHeapInterface;

public class NotExp implements  Exp {
    private Exp exp;
    public NotExp(Exp e){
        exp= e;
    }
    public int eval(MyDictionaryInterface<String, Integer> st, MyHeapInterface<Integer, Integer> h){
        int a = exp.eval(st, h);
        if(a==0)return 1;
        return 0;
    }
    @Override
    public String toString(){
        return "Not("+exp+")";
    }
}
