package model.exc;

public class EmptyException extends Exception{
    private String message;
    public EmptyException(String msg){
        message = msg;
    }
}