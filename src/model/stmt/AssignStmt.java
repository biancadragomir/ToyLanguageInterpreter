package model.stmt;

import model.PrgState;
import model.adt.interfaces.MyDictionaryInterface;
import model.adt.interfaces.MyHeapInterface;
import model.exp.Exp;

public class AssignStmt implements Statement {

    private String varName;
    private Exp value;
    public AssignStmt(String n, Exp v){
        varName=n;
        value = v;
    }

    @Override
    public PrgState execute(PrgState ps){
        MyDictionaryInterface<String, Integer> st = ps.getSymTable();
        MyHeapInterface<Integer, Integer > hp = ps.getHeap();
        int e = value.eval(st, hp);
        if(st.contains(varName))
            st.update(varName, e);
        else
            st.add(varName, e);
        return null;
    }
    @Override
    public String toString(){
        return "" + varName +" = " + value +";";
    }
}
