package model.stmt;

import model.PrgState;
import model.adt.interfaces.MyHeapInterface;

public class Sleep implements Statement {
    private int value;
    public Sleep(int a){
        value = a;
    }
    public PrgState execute(PrgState ps){
        if(value>0){
            value--;
            ps.getExecStack().push(this);
        }
        return null;
    }

    public String toString(){
        return "Sleep("+value+")";
    }

}
