package model.exc;

public class MyStmtExecException extends Exception{
    private String str;
    public MyStmtExecException(String s){
        str= s;
    }
}
