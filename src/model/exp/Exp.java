package model.exp;

import model.adt.interfaces.MyDictionaryInterface;
import model.adt.interfaces.MyHeapInterface;

public interface Exp {
    int eval(MyDictionaryInterface<String, Integer> st, MyHeapInterface<Integer, Integer> h); // the symbol table dict
}
