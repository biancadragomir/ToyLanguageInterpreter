package model.adt;

import java.io.BufferedReader;

public class FileData {
    private String filename;
    private BufferedReader br;
    public FileData(String f, BufferedReader b){
        filename = f;
        br = b; // this is the file descriptor
    }

    @Override
    public String toString(){
        return filename;
    }

    public BufferedReader getBr() {
        return br;
    }

    public String getFilename() {
        return filename;
    }

    public void setBr(BufferedReader br) {
        this.br = br;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }


}
