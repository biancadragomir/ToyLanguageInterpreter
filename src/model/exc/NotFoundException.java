package model.exc;

public class NotFoundException extends Exception {
    private String message;
    public NotFoundException(String s){
        message = s;
    }
}