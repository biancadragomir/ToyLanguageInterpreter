package model.stmt;
/*2.3. Implement the Statement: readFile(exp_file_id, var_name), where exp_file_id is an
expression and var_name is variable name. Its execution on the ExeStack is the following:
• pop the statement
• evaluate exp_file_id to a value
• using the previous step value we get the BufferedReader object associated in the FileTable.
If there is not any entry associated to this value in the FileTable we stop the execution with
an appropriate error message.
• Reads a line from the file using readLine method of the BufferedReader object. If line is null
create a zero int value. Otherwise translate the returned String into an int value (using
Integer.parseInt(String)).
• Add a new mapping (var_name, int value computed at the previous step) into the SymTable.
If var_name exists in SymTable update its associated value instead of adding a new
mapping.*/

import model.adt.FileData;
import model.PrgState;
import model.adt.interfaces.MyDictionaryInterface;
import model.adt.interfaces.MyHeapInterface;
import model.exp.Exp;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements Statement {
    private Exp expFileId;
    private String varName;
    public ReadFile(Exp e, String v){
        expFileId = e;
        varName = v;
    }

    @Override
    public String toString(){
        return "read("+varName+")";
    }

    public Exp getExpFileId() {
        return expFileId;
    }

    public String getVarName() {
        return varName;
    }

    public void setExpFileId(Exp expFileId) {
        this.expFileId = expFileId;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState ps) {
        MyDictionaryInterface<String, Integer> symTable = ps.getSymTable();
        MyHeapInterface<Integer, Integer > hp = ps.getHeap();

        int val = expFileId.eval(symTable, hp);
        FileData fd = ps.getFileTable().get(val);

        try{
            BufferedReader bf = fd.getBr();
            int nr=-1;
            String line="";
            try{
                line = bf.readLine();
                if( line.equals("")){
                    nr = 0;

                }

            }catch(IOException e){
                System.out.println(e.getMessage());

            }
            if(nr==-1){
                nr = Integer.parseInt(line);

            }

            if(symTable.contains(varName)){
                ps.getSymTable().update(varName, nr);
            }
            else{
                ps.getSymTable().add(varName, nr) ;
            }

        }catch(Exception e){
            System.err.println("error reading file! "+e.getMessage());
        }


        return null;
    }
}