package model;

import model.adt.*;
import model.adt.interfaces.*;
import model.exc.EmptyException;
import model.stmt.Statement;

import java.util.List;

public class PrgState {
    private MyStackInterface<Statement> execStack;
    private MyDictionaryInterface<String, Integer> symTable;
    private MyListInterface<Integer> messages;
    private Statement rootP;
    private FileTable<Integer, FileData> fileTable;
    private MyHeapInterface<Integer, Integer> heap;
    private int id;
    private static int nr = 0;
    private CyclicBarrier<Integer, BarrierPair<List<Integer>, Integer>> cyclicBarrier;
    private LatchTable<Integer, Integer> latchTable;
    private LockTableInterface<Integer, Integer> lockTable;

    public MyHeapInterface<Integer, Integer> getHeap() {
        return heap;
    }

    public void setHeap(MyHeap<Integer, Integer> heap) {
        this.heap = heap;
    }

    public PrgState(MyStackInterface<Statement> es, MyDictionaryInterface<String, Integer> st,
                    MyListInterface<Integer> m, Statement s, FileTable<Integer, FileData> ft,
                    MyHeapInterface<Integer,Integer> hp, CyclicBarrier<Integer, BarrierPair<List<Integer>, Integer>> cyclicBarrier,
                    LatchTable<Integer, Integer> latchTable, LockTableInterface<Integer, Integer> lockTable){
        execStack = es;
        symTable = st;
        messages = m;
        rootP = s;
        fileTable = ft;
        heap = hp;
        id= IdGenerator.generateID();
        nr=nr*10;
        this.cyclicBarrier = cyclicBarrier;
        this.latchTable = latchTable;
        this.lockTable=lockTable;
    }

    public static int getNr() {
        return nr;
    }

    public LatchTable<Integer, Integer> getLatchTable() {
        return latchTable;
    }

    public LockTableInterface<Integer, Integer> getLockTable() {
        return lockTable;
    }

    public CyclicBarrier<Integer, BarrierPair<List<Integer>, Integer>> getCyclicBarrier() {
        return cyclicBarrier;
    }
    public PrgState oneStep(){
        try{
            if(execStack.isEmpty()) throw new EmptyException("empty stack! ");
            Statement crtStmt = execStack.pop();
            return crtStmt.execute(this);
        }
        catch(EmptyException e){
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String toString(){
        StringBuffer sb  = new StringBuffer();
        sb.append("PrgState ID: "+ id);
        sb.append("\nstack: \n");
        sb.append(execStack);
        sb.append("\nsym table: \n");
        sb.append(symTable);
        sb.append("\nout: \n");
        sb.append(messages);
        sb.append("\nbarrier: \n");
        sb.append(cyclicBarrier);
//        sb.append("\nroot program: \n");
//        sb.append(rootP);
        return sb.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MyStackInterface<Statement> getExecStack(){
        return execStack;
    }

    public MyDictionaryInterface<String, Integer> getSymTable(){
        return symTable;
    }

    public MyListInterface<Integer> getMessages() {
        return messages;
    }

    public Statement getRootP() {
        return rootP;
    }

    public void setExecStack(MyStack<Statement> es){
        execStack = es;
    }

    public void setMessages(MyList<Integer> messages) {
        this.messages = messages;
    }

    public void setRootP(Statement rootP) {
        this.rootP = rootP;
    }

    public void setSymTable(MyDictionary<String, Integer> symTable) {
        this.symTable = symTable;
    }

    public FileTable<Integer, FileData> getFileTable() {
        return fileTable;
    }

    public void setFileTable(FileTable<Integer, FileData> fileTable) {
        this.fileTable = fileTable;
    }

    public boolean isNotCompleted(){
        return !execStack.isEmpty();
    }
}

