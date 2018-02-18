package model.stmt;

import model.PrgState;
import model.adt.interfaces.MyDictionaryInterface;
import model.adt.interfaces.MyHeapInterface;
import model.exp.Exp;

public class IncrementStmt implements Statement {

    private String varName;
    public IncrementStmt(String n){
        varName=n;
    }

    @Override
    public PrgState execute(PrgState ps){
        MyDictionaryInterface<String, Integer> st = ps.getSymTable();
        MyHeapInterface<Integer, Integer > hp = ps.getHeap();

        try{
            if(!st.contains(varName))
                throw new Exception("value not in sym table for increment! ");
            else{
                int e = st.get(varName);
                System.out.println("e is " + e);
                e++;
                System.out.println("e is " + e);

                //st.update(varName, e);
                ps.getSymTable().update(varName, e);
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }


        return null;
    }
    @Override
    public String toString(){
        return "" + varName +"--";
    }
}
