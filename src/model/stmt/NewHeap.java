package model.stmt;

import model.IdGenerator;
import model.PrgState;
import model.adt.interfaces.MyDictionaryInterface;
import model.adt.interfaces.MyHeapInterface;
import model.exp.Exp;

public class NewHeap implements Statement{
    private String varName;
    private Exp exp;
    public NewHeap(String s, Exp e){
        varName = s;
        exp = e;
    }

    @Override
    public PrgState execute(PrgState ps) {
        MyDictionaryInterface<String, Integer> st = ps.getSymTable();
        MyHeapInterface<Integer, Integer>  heap = ps.getHeap();
        int e = exp.eval(st, heap);
        int k = IdGenerator.generateID();
        heap.add(k, e);
        if(st.contains(varName)) st.update(varName, k);
        else st.add(varName, k);
        return null;
    }

    @Override
    public String toString() {
        return "new(" + varName +','+  exp + ");";
    }
}
