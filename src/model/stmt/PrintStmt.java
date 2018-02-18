package model.stmt;

import model.PrgState;
import model.adt.interfaces.MyDictionaryInterface;
import model.adt.interfaces.MyHeapInterface;
import model.exp.Exp;

public class PrintStmt implements Statement{
    private Exp expr;
    public PrintStmt(Exp e){
        expr = e;
    }

    @Override
    public PrgState execute(PrgState ps) {
        MyDictionaryInterface<String, Integer > st = ps.getSymTable();
        MyHeapInterface<Integer, Integer > hp = ps.getHeap();

        int e = expr.eval(st, hp);
        ps.getMessages().add(e);
        return null;
    }

    @Override
    public String toString(){
        return "Print(" + expr + ")";
    }
}
