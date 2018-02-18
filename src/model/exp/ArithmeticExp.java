package model.exp;

import model.adt.interfaces.MyDictionaryInterface;
import model.adt.interfaces.MyHeapInterface;
import model.exc.DivByZero;
import model.exc.UnknownOpExc;

public class ArithmeticExp implements Exp {

    private char op;
    private Exp left, right;

    public ArithmeticExp(char o, Exp l, Exp r) {
        left = l;
        right = r;
        op = o;
    }

    public int eval(MyDictionaryInterface<String, Integer> st, MyHeapInterface<Integer, Integer> h){
        int l = left.eval(st, h);
        int r = right.eval(st, h);
        try{
            switch(op){
                case '+':
                    return l + r;
                case '-':
                    return l-r;
                case '*':
                    return l*r ;

                case ':':
                    try{
                        if(r ==0)
                            throw new DivByZero("division by zero! ");
                        else{
                            return l/r;
                        }
                    }
                    catch (DivByZero e){
                        System.out.println("division by zero! ");
                    }
                default:
                    throw new UnknownOpExc("unknown operator!");
            }
        }
        catch (UnknownOpExc e){
            System.err.println("unknown operator! "+ e.getMessage());
            return -1;
        }
    }
    @Override
    public String toString(){
        return ""+left+op+right;
    }
}
