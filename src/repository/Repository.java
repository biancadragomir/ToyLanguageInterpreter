package repository;

import model.PrgState;
import model.adt.interfaces.MyDictionaryInterface;
import model.stmt.Statement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements RepositoryInterface {
    private String logFilePath;
    private List<PrgState> prgStates;

    public Repository(String f){
        prgStates = new ArrayList<>();
        logFilePath = f;
    }
    public void addPrgState(PrgState ps){
        prgStates.add(ps);
    }
    public void logPrgStateExec(PrgState ps){

        try(PrintWriter logFile= new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))){
            logFile.println("PrgState ID "+ps.getId());
            logFile.println("Exe stack: ");
            for(Statement s: ps.getExecStack().getAll()){
                logFile.println(s);
            }

            logFile.println("Sym table: ");
            MyDictionaryInterface<String, Integer> symTable= ps.getSymTable();
            for(String s: symTable.getAll()){
                logFile.println(s + "->" +symTable.get(s));
            }

            logFile.println("Out: ");
            for(Integer i: ps.getMessages().getAll()){
                logFile.println(i);
            }

            logFile.println("File table: ");
            for(Integer k: ps.getFileTable().getAll()){
                logFile.println(k +"-->"+ps.getFileTable().get(k).toString());
            }
            logFile.println("-----------------------------------------------------");

        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void setPrgList(List<PrgState> l) {
        prgStates=l;
    }

    @Override
    public List<PrgState> getPrgList() {
        return prgStates;
    }
}