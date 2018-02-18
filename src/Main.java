import controller.Controller;
import model.adt.FileData;
import model.adt.FileTable;
import model.PrgState;
import model.adt.*;
import model.adt.interfaces.*;
import model.exp.*;
import model.stmt.*;
import repository.Repository;
import repository.RepositoryInterface;
import view.commands.RunExample;
import view.commands.TextMenu;
import view.commands.ExitCommand;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        CyclicBarrier<Integer, BarrierPair<List<Integer>, Integer>> cyclicBarrier1 = new CyclicBarrier<>();

        Statement st = new CompStmt(new AssignStmt("v", new ConstExpr(2)), new PrintStmt(new VarExp("v")));
        MyStackInterface<Statement> es = new MyStack<>();
        Statement st1 = new OpenRFile("f1", "C:\\Users\\Bianca\\Desktop\\NewToyInterpreterFX_Practice\\src\\inFile1");
        Statement st2 = new ReadFile(new VarExp("f1"), "a");
        Statement st6 = new PrintStmt(new VarExp("a"));
        Statement cs = new CompStmt(st1, st2);

        Statement st3 = new CloseRFile(new VarExp("f1"));
        Statement st4 = new IfStmt(new VarExp("a"), new ReadFile(new VarExp("f1"), "a"), new PrintStmt(new ConstExpr(0)) );
        Statement st5 = new PrintStmt(new VarExp("a"));
        es.push(st3);
        es.push(st5);
        es.push(st4);
        es.push(st6);
        es.push(cs);

        LatchTable<Integer, Integer> latchTable1 = new LatchTable<>();
        MyDictionaryInterface<String, Integer> dict = new MyDictionary<>();
        MyListInterface<Integer> list = new MyList<>();
        FileTable<Integer, FileData> ft = new FileTable<>();
        MyHeapInterface<Integer, Integer> heap = new MyHeap<>();
        LockTableInterface<Integer, Integer> lockT1 = new LockTable<>();
        PrgState ps = new PrgState(es, dict, list, st, ft, heap, cyclicBarrier1, latchTable1, lockT1);
        RepositoryInterface repo = new Repository("logFile1");
        repo.addPrgState(ps);
        Controller ctrl1 = new Controller(repo);


        MyStackInterface<Statement> es2 = new MyStack<>();
        es2.push(st);
        MyDictionaryInterface<String, Integer> dict2 = new MyDictionary<>();
        MyListInterface<Integer> list2 = new MyList<>();
        FileTable<Integer, FileData> ft2 = new FileTable<>();
        MyHeapInterface<Integer, Integer> hp2 = new MyHeap<>();
        CyclicBarrier<Integer, BarrierPair<List<Integer>, Integer>> cyclicBarrier2 = new CyclicBarrier<>();

        LatchTable<Integer, Integer> latchTable2 = new LatchTable<>();
        LockTableInterface<Integer, Integer> lockT2 = new LockTable<>();

        PrgState ps2 = new PrgState(es2, dict2, list2, st2, ft2, hp2, cyclicBarrier2, latchTable2, lockT2);
        RepositoryInterface repo2 = new Repository("logFile2");
        repo2.addPrgState(ps2);
        Controller ctrl2 = new Controller(repo2);

        MyStackInterface<Statement> es3 = new MyStack<>();
        Statement forkStmt = new CompStmt(new CompStmt(new AssignStmt("nr1", new ConstExpr(2)), new PrintStmt(new VarExp("nr1"))),
                new CompStmt(new AssignStmt("nr2", new ConstExpr(10)), new PrintStmt(new VarExp("nr2"))));
        //Statement forkStmt = new CompStmt(new AssignStmt("nr1", new ConstExpr(2)), new PrintStmt(new VarExp("nr1")));

        Statement state2 = new ForkStmt(forkStmt);
        es3.push(state2);

        MyDictionaryInterface<String, Integer> dict3 = new MyDictionary<>();
        MyListInterface<Integer> list3 = new MyList<>();
        FileTable<Integer, FileData> ft3 = new FileTable<>();
        MyHeapInterface<Integer, Integer> hp3 = new MyHeap<>();
        CyclicBarrier<Integer, BarrierPair<List<Integer>, Integer>> cyclicBarrier3 = new CyclicBarrier<>();
        LatchTable<Integer, Integer> latchTable3 = new LatchTable<>();

        LockTableInterface<Integer, Integer> lockT3 = new LockTable<>();

        PrgState ps3 = new PrgState(es3, dict3, list3, st3, ft3, hp3, cyclicBarrier3, latchTable3, lockT3);
        RepositoryInterface repo3 = new Repository("logFile3");
        repo3.addPrgState(ps3);
        Controller ctrl3 = new Controller(repo3);

        //Fork Statement
        //v=10;new(a,22); fork(wH(a,30);v=32;print(v);print(rH(a)));   print(v);print(rH(a))
        Statement ex4 = new CompStmt(
                new CompStmt(
                        new CompStmt(
                                new AssignStmt("v", new ConstExpr(10)),
                                new NewHeap("a", new ConstExpr(22))
                        ),
                        new ForkStmt(
                                new CompStmt(
                                        new CompStmt(
                                                new WriteHeap("a", new ConstExpr(30)),
                                                new AssignStmt("v", new ConstExpr(32))
                                        ),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new ReadHeap("a"))
                                        )
                                )
                        )
                ),
                new CompStmt(
                        new PrintStmt(new VarExp("v")),
                        new PrintStmt(new ReadHeap("a"))
                )
        );
        MyDictionaryInterface<String, Integer> d4 = new MyDictionary<>();
        MyStackInterface<Statement> es4 = new MyStack<>();
        es4.push(ex4);
        MyListInterface<Integer> l4 = new MyList<>();
        FileTable<Integer, FileData> f4= new FileTable<>();
        MyHeapInterface<Integer, Integer> h4 = new MyHeap<>();
        CyclicBarrier<Integer, BarrierPair<List<Integer>, Integer>> cyclicBarrier4 = new CyclicBarrier<>();
        LatchTable<Integer, Integer> latchTable4 = new LatchTable<>();
        LockTableInterface<Integer, Integer> lockT4 = new LockTable<>();

        PrgState ps4 = new PrgState(es4, d4, l4, ex4, f4, h4, cyclicBarrier4, latchTable4, lockT4);
        RepositoryInterface repo4 = new Repository("logFile4");
        repo4.addPrgState(ps4);
        Controller ctrl4 = new Controller(repo4);

        Statement stmt5 =
 new CompStmt(
                new CompStmt(
                        new AssignStmt("v",
                                new ConstExpr(7)),
                        new WhileStmt(
                                new BooleanExpr(
                                        new VarExp("v"), ">",
                                        new ConstExpr(4)),
                                new CompStmt(
                                        new PrintStmt(new VarExp("v")),
                                        new AssignStmt("v",
                                                new ArithmeticExp('-',
                                                        new VarExp("v"),
                                                        new ConstExpr(1)))
                                ))
                ),
                new PrintStmt(new VarExp("v"))
        );

        MyDictionaryInterface<String, Integer> d5 = new MyDictionary<>();
        MyStackInterface<Statement> es5 = new MyStack<>();
        es5.push(stmt5);
        MyListInterface<Integer> l5 = new MyList<>();
        FileTable<Integer, FileData> f5= new FileTable<>();
        MyHeapInterface<Integer, Integer> h5 = new MyHeap<>();
        CyclicBarrier<Integer, BarrierPair<List<Integer>, Integer>> cyclicBarrier5 = new CyclicBarrier<>();
        LatchTable<Integer, Integer> latchTable5 = new LatchTable<>();
        LockTableInterface<Integer, Integer> lockT5 = new LockTable<>();

        PrgState ps5 = new PrgState(es5, d5, l5, stmt5, f5, h5, cyclicBarrier5, latchTable5, lockT5);
        RepositoryInterface repo5 = new Repository("logFile5");
        repo5.addPrgState(ps5);
        Controller ctrl5 = new Controller(repo5);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1","(open(inputFile) read(a)) " +
                "Print(a)" +
                "if a then read(a) else Print(0)" +
                "Print(a)" +
                "close(f1)",ctrl1));
        menu.addCommand(new RunExample("2","(v = 2 Print(v)) ",ctrl2));
        menu.addCommand(new RunExample("3","ForkStmt(((nr1 = 2 Print(nr1))  (nr2 = 10 Print(nr2)) ) )",ctrl3));
        menu.addCommand(new RunExample("4","(((v = 10; new(a,22);)  ForkStmt(((WriteHeap(a, 30) v = 32)  (Print(v) Print(readHeap(a))) ) ))  (Print(v) Print(readHeap(a))) ) ",ctrl4));
        menu.addCommand(new RunExample("5","((v = 7 while model.exp.BooleanExpr@5383967b ( (Print(v) v = v-1)  ))  Print(v)) ",ctrl5));
        menu.show();
    }
}

