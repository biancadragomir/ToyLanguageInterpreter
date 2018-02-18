package model.exc;

public class UnknownOpExc extends  Exception {
    private String message;
    public UnknownOpExc(String m){
        message = m;
    }
}
