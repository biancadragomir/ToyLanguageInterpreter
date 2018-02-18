package model.stmt.latchStuff;

import model.IdGenerator;
import model.PrgState;
import model.adt.interfaces.MyDictionaryInterface;
import model.adt.interfaces.MyHeapInterface;
import model.exp.Exp;
import model.stmt.Statement;

public class NewLatch implements Statement {

    private String var;
    private Exp exp;

    public NewLatch(String v, Exp e){
        var =v; exp = e;
    }

    @Override
    public PrgState execute(PrgState ps) {
        MyDictionaryInterface<String, Integer> symTable = ps.getSymTable();
        MyHeapInterface<Integer, Integer> heapTable = ps.getHeap();

        int number = exp.eval(symTable, heapTable);

        synchronized (ps.getLatchTable()){
            System.out.println("current ps synchronized: "+ ps.getId());
            int newId = IdGenerator.generateID();
            ps.getLatchTable().add(newId, number);

            if(symTable.contains(var)){
                symTable.update(var, newId);
            }else symTable.add(var, newId);
        }
        return null;
    }
    public String toString(){
        return "new Latch("+var+", ("+exp+") ";
    }
}
