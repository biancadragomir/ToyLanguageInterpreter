package model.stmt;

import model.PrgState;
import model.adt.interfaces.MyHeapInterface;
import model.exp.Exp;

public class WhileStmt implements Statement {
    private Exp cond;
    private Statement body;
    public WhileStmt(Exp c, Statement b){
        cond = c;
        body = b;
    }

    @Override
    public PrgState execute(PrgState ps) {
        MyHeapInterface<Integer, Integer > hp = ps.getHeap();
            int e = cond.eval(ps.getSymTable(), hp);
            if(e!=0){
                ps.getExecStack().push(this);
                ps.getExecStack().push(body);
            }
        return null;
    }

    @Override
    public String toString() {
        return "while "+ cond+ " ( "+ body+ " )";
    }
}