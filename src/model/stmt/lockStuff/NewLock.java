package model.stmt.lockStuff;

import model.IdGenerator;
import model.PrgState;
import model.stmt.Statement;

public class NewLock implements Statement {
    private String var;

    public String toString(){
        return "NewLock("+var+")";
    }

    @Override
    public PrgState execute(PrgState ps) {
        int newFreeLoc = IdGenerator.generateID();

        synchronized (ps.getLockTable()){
            ps.getLockTable().add(newFreeLoc, -1);
        }
        if(ps.getSymTable().contains(var)){
            ps.getSymTable().update(var, newFreeLoc);
        }else{
            ps.getSymTable().add(var, newFreeLoc);
        }

        return null;
    }

    public NewLock(String v){
        var=v;
    }



}
