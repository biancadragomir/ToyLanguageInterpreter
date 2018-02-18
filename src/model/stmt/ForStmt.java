package model.stmt;

import model.PrgState;
import model.exp.BooleanExpr;
import model.exp.Exp;
import model.exp.VarExp;

public class ForStmt implements Statement {

    private Exp e1, e2, e3;
    private Statement stmt;

    public ForStmt(Exp ex1, Exp ex2, Exp ex3, Statement s){
        e1 = ex1; e2 = ex2; e3 = ex3; stmt = s;
    }
    public String toString(){
        return "for("+e1+";"+e2+";"+e3+"){"+stmt+"}";
    }

    @Override
    public PrgState execute(PrgState ps) {

        Statement s = new CompStmt(
                new AssignStmt("v", e1),
                new WhileStmt(e2,new CompStmt(
                        stmt, new AssignStmt("v", e3))) );


        ps.getExecStack().push(s);
        return null;
    }
}
