package model.stmt;

import model.adt.FileData;
import model.PrgState;
import model.adt.interfaces.MyDictionaryInterface;
import model.adt.interfaces.MyHeapInterface;
import model.exp.Exp;
import repository.exc.InterpreterException;

import java.io.BufferedReader;
import java.io.IOException;

/*2.4. Implement the Statement: closeRFile(exp_file_id), where exp_file_id is an expression. Its
execution on the ExeStack is the following:
• pop the statement
• evaluate exp_file_id to a value. If any error occurs then terminate the execution with an
appropriate error message.
• Use the value (computed at the previous step) to get the entry into the FileTable and get the
associated BufferedReader object. If there is not any entry in FileTable for that value we
stop the execution with an appropriate error message.
• call the close method of the BufferedReader object
• delete the entry from the FileTable*/
public class CloseRFile implements Statement {
    private Exp expFileId;
    public CloseRFile(Exp e){
        expFileId = e;
    }
    @Override
    public String toString(){
        return "close("+expFileId.toString()+")";
    }
    public PrgState execute(PrgState ps){
        MyDictionaryInterface<String, Integer> symTable = ps.getSymTable();
        MyHeapInterface<Integer, Integer > hp = ps.getHeap();

        int value = expFileId.eval(symTable, hp);

        try{
            if(!ps.getFileTable().contains(value))
                throw new InterpreterException("No entry like that in file table!");



        }catch (InterpreterException e){
            System.out.println(e.getMessage());
        }
        try{
            FileData fd = ps.getFileTable().get(value);
            BufferedReader br = fd.getBr();
            try{
                br.close();
            }
            catch(IOException e){
                System.out.println(e.getMessage());
            }
            ps.getFileTable().remove(value);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }

        return null;
    }
}