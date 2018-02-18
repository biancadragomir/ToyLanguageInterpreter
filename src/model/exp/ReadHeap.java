package model.exp;

import model.adt.interfaces.MyDictionaryInterface;
import model.adt.interfaces.MyHeapInterface;
import repository.exc.InterpreterException;

public class ReadHeap implements Exp {
    private String var;
    public ReadHeap(String var) {
        this.var = var;
    }

    @Override
    public int eval (MyDictionaryInterface<String, Integer> st, MyHeapInterface<Integer, Integer> h){
        try{
            if(!st.contains(var))
                throw new InterpreterException("There is no such thing as that heap value in the sym table");
            if(!h.contains(st.get(var)))
                throw new InterpreterException("Heap does not contain the variable!!");
            return h.get(st.get(var));
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
        return -3;
    }
    public String toString(){
        return "readHeap("+var+")";
    }
}
