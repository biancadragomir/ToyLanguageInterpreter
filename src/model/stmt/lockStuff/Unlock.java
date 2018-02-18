package model.stmt.lockStuff;

import model.PrgState;
import model.stmt.Statement;

public class Unlock implements Statement {
    private String var;

    public Unlock(String v){
        var = v;
    }

    public String toString(){
        return "Unlock("+var+")";
    }

    @Override
    public PrgState execute(PrgState ps) {

        if(!ps.getSymTable().contains(var)){
            System.err.println("sym table does not contain that! ");
            return null;
        }

        int foundIndex = ps.getSymTable().get(var);

        synchronized (ps.getLockTable()){
            if (!ps.getLockTable().contains(foundIndex)){
                //do nothing
            }
            else {
                if (ps.getLockTable().get(foundIndex) == ps.getId()){
                    ps.getLockTable().update(foundIndex, -1);
                }
            }
        }

        return null;
    }
}
