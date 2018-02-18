package model.stmt;

import model.PrgState;

public class CompStmt implements Statement {
    private Statement first, second;
    public CompStmt(Statement f, Statement s){
        first = f;
        second = s;
    }

    @Override
    public PrgState execute(PrgState ps){
        ps.getExecStack().push(second);
        ps.getExecStack().push(first);
        return null;
    }

    @Override
    public String toString(){
        return "(" + first +" "+  second + ") ";
    }
}
