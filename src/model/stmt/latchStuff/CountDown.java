package model.stmt.latchStuff;

import model.PrgState;
import model.adt.LatchTable;
import model.adt.interfaces.MyDictionaryInterface;
import model.stmt.Statement;

public class CountDown implements Statement {
    private String var;

    public CountDown(String v){
        var = v;
    }

    public String toString(){
        return "CountDown("+var+")";
    }

    @Override
    public PrgState execute(PrgState ps) {
        MyDictionaryInterface<String, Integer> symTable =ps.getSymTable();
        if(!symTable.contains(var)){
            System.err.println("variable not found in sym table! ");
            return null;
        }
        synchronized(ps.getLatchTable()){
            LatchTable<Integer, Integer> lt = ps.getLatchTable();
            int foundIndex = symTable.get(var);
            synchronized (ps.getLatchTable()){
                if(!lt.contains(foundIndex)){
                    System.out.println("no index in latch table (latch countdown)");
                    //do nothing
                }else {
                    int ind = lt.get(foundIndex);
                    if(ind > 0){
                        ps.getLatchTable().update(foundIndex, ind-1);
                        ps.getMessages().add(ps.getId());
                    }

                }
            }
        }
        return null;
    }
}
