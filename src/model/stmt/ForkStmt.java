package model.stmt;

import model.PrgState;
import model.adt.interfaces.MyDictionaryInterface;
import model.adt.MyStack;
import model.adt.interfaces.MyStackInterface;

public class ForkStmt implements Statement {

//    private Statement stmt;
//    public ForkStmt(Statement s){
//        stmt = s;
//    }
//
//    public PrgState execute(PrgState ps){
//        MyStackInterface<Statement> execStack = new MyStack<>();
//        execStack.push(stmt);
//        MyHeapInterface<Integer, Integer> heap = ps.getHeap();
//        FileTable<Integer, FileData> fileTable = ps.getFileTable();
//        MyListInterface<Integer> output = ps.getMessages();
//        MyDictionaryInterface<String, Integer> symT = ps.getSymTable().copy();
//        return new PrgState(execStack,symT,output,stmt,fileTable,heap);
//    }
//
//    @Override
//    public String toString() {
//        return "Fork(" +
//                stmt +
//                ')';
//    }

    private Statement statement;

    public ForkStmt(Statement statement) {
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState prgState) {
        MyDictionaryInterface<String, Integer> map = prgState.getSymTable().copy();
        MyStackInterface<Statement> stack = new MyStack<>();
        stack.push(statement);
        return new PrgState(stack, map,prgState.getMessages(), statement, prgState.getFileTable(),
                prgState.getHeap(), prgState.getCyclicBarrier(), prgState.getLatchTable(), prgState.getLockTable());
    }

    @Override
    public String toString() {
        return "ForkStmt(" + statement + ")";
    }

}
