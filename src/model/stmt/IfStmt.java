package model.stmt;

import model.PrgState;
import model.adt.interfaces.MyHeapInterface;
import model.exp.Exp;

public class IfStmt implements Statement {
    private Exp cond;
    private Statement thenS, elseS;
    public IfStmt(Exp c, Statement t, Statement e){
        cond = c;
        thenS = t;
        elseS = e;
    }

    @Override
    public PrgState execute(PrgState ps) {
        MyHeapInterface<Integer, Integer > hp = ps.getHeap();

        int e = cond.eval(ps.getSymTable(), hp);
        if(e!=0){
            ps.getExecStack().push(thenS);
        }
        else
            ps.getExecStack().push(elseS);
        return null;
    }

    @Override
    public String toString() {
        return "if "+ cond+ " then "+ thenS + " else "+ elseS;
    }
}
