package model.stmt;

/* 2.2. Implement the Statement openRFile(var_file_id,filename), where var_file_id is a variable
name and filename is a string. Its execution on the ExeStack is the following:
• pop the statement
• check whether the filename is not already in the FileTable. If it exists stopped the execution
with an appropriate error message.
• open the file filename in Java using an instance of the BufferedReader class. If the file does
not exist or other IO error occurs stopped the execution with an appropriate error message.
• create a new entrance into the FileTable which maps a new unique integer key to the
(filename, instance of the BufferedReader class created before).
• set the var_file_id to that new unique integer key (created at the previous step) into the
SymTable.*/

import model.adt.FileData;
import model.adt.interfaces.FileTableInterface;
import model.IdGenerator;
import model.PrgState;
import repository.exc.InterpreterException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements Statement {
    private String varFileId, filename;
    public OpenRFile(String varFileId, String filename){
        this.varFileId = varFileId;
        this.filename = filename;
    }

    @Override
    public String toString(){
        return "open("+filename+")";
    }


    public PrgState execute(PrgState ps){
        //Statement stm = ps.getExecStack().pop();
        try{
            if(isOpen(ps))
                throw new InterpreterException("err");

            BufferedReader br = new BufferedReader(new FileReader(filename));
            FileData fd = new FileData(filename, br);
            int id = IdGenerator.generateID();
            ps.getFileTable().add(id, fd);
            if(ps.getSymTable().contains(varFileId)){
                ps.getSymTable().update(varFileId, id);
            }
            else
                ps.getSymTable().add(varFileId, id);
        }catch (FileNotFoundException e){
            System.out.println("file not found! ");

        }
        catch(NullPointerException nullp){
            System.out.println(nullp.getMessage());
        }
        return null;
    }

    private boolean isOpen(PrgState ps){
        FileTableInterface<Integer, FileData> ft = ps.getFileTable();
        for(FileData fd: ft.getValues()){
            if(fd.getFilename().equals(filename))
                return true;
        }
        return false;
    }


    public String getFilename() {
        return filename;
    }

    public String getVarFileId() {
        return varFileId;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setVarFileId(String varFileId) {
        this.varFileId = varFileId;
    }
}
