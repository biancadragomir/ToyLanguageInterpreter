package model.stmt;

import model.PrgState;
import model.adt.interfaces.MyHeapInterface;
import model.exp.Exp;
import model.exp.NotExp;

public class RepeatUntilStmt implements Statement {
    private Statement stmt;
    private Exp exp;

    public RepeatUntilStmt(Statement s, Exp e){
        stmt = s; exp = e;
    }

    @Override
    public PrgState execute(PrgState ps) {
        MyHeapInterface<Integer, Integer > hp = ps.getHeap();
        Statement newStmt = new CompStmt(stmt, new WhileStmt(new NotExp(exp), stmt));
        ps.getExecStack().push(newStmt);
        return null;
    }

    @Override
    public String toString(){
        return "Repeat("+stmt+") until("+exp+")";
    }
}
