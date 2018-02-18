package model.exc;

public class DivByZero extends Exception {
    private String message;
    public DivByZero(String msg){
        message = msg;
    }
}
