package model.stmt.latchStuff;

import model.PrgState;
import model.adt.interfaces.LatchTableInterface;
import model.adt.interfaces.MyDictionaryInterface;
import model.stmt.Statement;

public class LatchAwait implements Statement {
    private String var;

    public LatchAwait(String v){
        var = v;

    }

    @Override
    public PrgState execute(PrgState ps) {
        MyDictionaryInterface<String, Integer> symTable = ps.getSymTable();
        int foundIndex = symTable.get(var);

        LatchTableInterface<Integer, Integer> latchTable = ps.getLatchTable();
        if(!latchTable.contains(foundIndex)){
            System.err.println("index not found in latch table! ");
            return null;
        }
        else{
            if(latchTable.get(foundIndex) == 0){
                //do nothing
            }else {
                ps.getExecStack().push(this);
            }
        }
        return null;
    }
    public String toString(){
        return "Await("+var+")";
    }
}
