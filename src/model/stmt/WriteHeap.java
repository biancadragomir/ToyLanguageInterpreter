package model.stmt;

import model.PrgState;
import model.adt.interfaces.MyDictionaryInterface;
import model.adt.interfaces.MyHeapInterface;
import model.exp.Exp;
import repository.exc.InterpreterException;

public class WriteHeap implements Statement {
    private String var;
    private Exp expr;
    public WriteHeap(String v, Exp e){
        var = v;
        expr = e;
    }

    @Override
    public PrgState execute(PrgState ps) {
        MyDictionaryInterface<String, Integer> symTable = ps.getSymTable();
        MyHeapInterface<Integer, Integer> heap = ps.getHeap();

            try{
                int e = expr.eval(symTable, heap);
                if(!symTable.contains(var))
                    throw new InterpreterException("err");
                if(!heap.contains(symTable.get(var)))
                    throw new InterpreterException("err");
                heap.update(symTable.get(var), e);
            }catch(Exception e){
                System.err.println(e.getMessage());
            }

        return null;
    }

    @Override
    public String toString(){
        return "WriteHeap("+var+", "+expr+")";
    }
}
