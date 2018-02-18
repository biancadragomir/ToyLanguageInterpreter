package model.exc;

public class FullException extends Exception {
    private String message;
    public FullException(String msg){
        message = msg;
    }
}