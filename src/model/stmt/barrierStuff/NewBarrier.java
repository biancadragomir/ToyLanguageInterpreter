package model.stmt.barrierStuff;

import model.IdGenerator;
import model.PrgState;
import model.adt.BarrierPair;
import model.adt.interfaces.MyDictionaryInterface;
import model.adt.interfaces.MyHeapInterface;
import model.exp.Exp;
import model.stmt.Statement;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class NewBarrier implements Statement {
    private String var; private Exp exp;
    public NewBarrier(String var, Exp exp){
        this.var = var; this.exp = exp;
    }

    public String toString(){
        return "newBarrier("+var+", "+exp+")";
    }

    public PrgState execute(PrgState ps){
        MyDictionaryInterface<String, Integer> symTable = ps.getSymTable();
        MyHeapInterface<Integer, Integer> heap = ps.getHeap();
        int e = exp.eval(symTable, heap);
        synchronized (ps.getCyclicBarrier()){
            Integer newFreeLocation  = IdGenerator.generateID();
            ps.getCyclicBarrier().add(newFreeLocation, new BarrierPair<>(new ArrayList<Integer>(), e ));
            if(symTable.contains(var)){
                symTable.update(var, newFreeLocation);
            }else{
                symTable.add(var, newFreeLocation);
            }
        }
        return null;
    }
}
