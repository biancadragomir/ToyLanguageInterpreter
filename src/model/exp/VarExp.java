package model.exp;

import model.adt.interfaces.MyDictionaryInterface;
import model.adt.interfaces.MyHeapInterface;
import model.exc.NotFoundException;

public class VarExp implements Exp {
    private String varName;
    private int value;
    public VarExp(String v){
        varName = v;
    }

    public int getValue() {
        return value;
    }

    public int eval(MyDictionaryInterface<String, Integer> st, MyHeapInterface<Integer, Integer> h){
        try{
            if(st.contains(varName)){
                value=st.get(varName);
                return st.get(varName);
            }
            else
                throw new NotFoundException("variable not found! ");

        }
        catch(NotFoundException e) {
            System.err.println("Variable was not found! ("+e.getMessage()+") ");
            return -2;
        }
    }
    @Override
    public String toString(){
        return varName;
    }
}
