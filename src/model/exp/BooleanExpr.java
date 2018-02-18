package model.exp;

import model.adt.interfaces.MyDictionaryInterface;
import model.adt.interfaces.MyHeapInterface;
import model.exc.UnknownOpExc;

public class BooleanExpr implements Exp {
    private Exp e1, e2;
    private String op;

    public BooleanExpr(Exp ex1, String opr, Exp ex2){
        e1 = ex1;
        op = opr;
        e2 = ex2;
    }

    @Override
    public String toString(){
        return "" + e1 + op +e2;
    }

    public int eval(MyDictionaryInterface<String, Integer> st, MyHeapInterface<Integer, Integer> h){
        int l = e1.eval(st, h);
        int r = e2.eval(st, h);
        try{

            if(op.equals("<")){
                if(l<r)return 1;
                else return 0;
            }
            if(op.equals("<=")){
                if(l<=r)return 1;
                else return 0;
            }
            if(op.equals("==")){
                if(l==r)return 1;
                else return 0;
            }
            if(op.equals("!=")){
                if(l!=r)return 1;
                else return 0;
            }
            if(op.equals(">")){
                if(l>r)return 1;
                else return 0;
            }
            if(op.equals(">=")){
                if(l>=r)return 1;
                else return 0;
            }
            throw new UnknownOpExc("unknown op");
        }
        catch (UnknownOpExc e){
            System.out.println("unknown operator! ");
            return -1;
        }
    }
}
