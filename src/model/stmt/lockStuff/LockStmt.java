package model.stmt.lockStuff;

import model.PrgState;
import model.stmt.Statement;

public class LockStmt implements Statement {

    private String var;

    public String toString(){
        return "Lock("+var+")";
    }

    public LockStmt(String v){
        var = v;
    }

    @Override
    public PrgState execute(PrgState ps) {

        if(!ps.getSymTable().contains(var)){
            System.err.println("Sym table does not contain that lock var! ");
            return null;
        }
        int foundIndex = ps.getSymTable().get(var);

        synchronized (ps.getLockTable()){
            if(!ps.getLockTable().contains(foundIndex)){
                System.err.println("lock table does not contain that found index! ");
                return null;
            }else{
                if(ps.getLockTable().get(foundIndex) == -1){
                    ps.getLockTable().update(foundIndex, ps.getId());

                }else{
                    ps.getExecStack().push(this);
                }
            }
        }

        return null;
    }
}
