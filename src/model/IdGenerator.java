package model;

public class IdGenerator {
    private static int curr = 1;
    public static int generateID(){
        return curr ++;
    }
}
