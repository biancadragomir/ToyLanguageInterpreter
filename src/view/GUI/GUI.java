package view.GUI;

import controller.Controller;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.PrgState;
import model.adt.*;
import model.adt.interfaces.*;
import model.exp.*;
import model.stmt.*;
import model.stmt.barrierStuff.Await;
import model.stmt.barrierStuff.NewBarrier;
import model.stmt.latchStuff.CountDown;
import model.stmt.latchStuff.LatchAwait;
import model.stmt.latchStuff.NewLatch;
import model.stmt.lockStuff.LockStmt;
import model.stmt.lockStuff.NewLock;
import model.stmt.lockStuff.Unlock;
import repository.Repository;
import repository.RepositoryInterface;
import view.GUI.TableViewStuff.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GUI extends Application {

    public static Statement getStmt9(){
        Exp rv1 = new ReadHeap("v1");Exp rv2 = new ReadHeap("v2");Exp rv3 = new ReadHeap("v3");
        Statement wv1 = new WriteHeap("v1",new ArithmeticExp('*',rv1,new ConstExpr(10)));
        Statement wv2 = new WriteHeap("v2",new ArithmeticExp('*',rv2,new ConstExpr(10)));
        Statement wv3 = new WriteHeap("v3",new ArithmeticExp('*',rv3,new ConstExpr(10)));

        Statement nv1 = new NewHeap("v1",new ConstExpr(2));
        Statement nv2 = new NewHeap("v2",new ConstExpr(3));
        Statement nv3 = new NewHeap("v3",new ConstExpr(4));
        Statement nbarr = new NewBarrier("cnt",rv3);
        Statement await = new Await("cnt");
        Statement declarations = new CompStmt(new CompStmt(nv1,nv2), new CompStmt(nv3,nbarr));

        Statement line2 = new ForkStmt(new CompStmt(wv1,new CompStmt(new PrintStmt(rv1), await)));
        Statement line3 = new ForkStmt(new CompStmt(new CompStmt(await,wv2),new PrintStmt(rv2)));
        Statement line4 = new ForkStmt(new CompStmt(new CompStmt(await,wv3),new CompStmt(wv3,new PrintStmt(rv3))));
        Statement line5 = new CompStmt(await,new PrintStmt(new ConstExpr(2000)));

        Statement ex9 =  new CompStmt(new CompStmt(new CompStmt(declarations,line2), new CompStmt(line3, line4)),line5);
        return ex9;
    }

    public static Statement getStmt10(){
        /*new(v1,20);---
        new(v2,30);---
        newLock(x);---s1
        fork(
           fork(
               lock(x);wh(v1,rh(v1)-1);unlock(x)
           );--
           lock(x);wh(v1,rh(v1)+1);unlock(x)---
        );---s2
        newLock(q);---s3
        fork(
            fork(
                lock(q);wh(v2,rh(v2)+5);unlock(q)
            );
            m=100;lock(q);wh(v2,rh(v2)+1);unlock(q)
        );----s4
        z=200;z=300;z=400;---s5
        lock(x);
        print (rh(v1));---s6
        unlock(x);
        lock(q);
        print(rh(v2));----s7
        unlock(q)
        The final Out should be {20,36}*/

        Statement s1 = new CompStmt(
                new NewHeap("v1", new ConstExpr(20)),
                new CompStmt(
                        new NewHeap("v2", new ConstExpr(30)),
                        new NewLock("x")
                )
        );
        Statement s2 =new ForkStmt(
                new CompStmt(
                        new ForkStmt(
                                new CompStmt(
                                        new LockStmt("v"),
                                        new CompStmt(
                                                new WriteHeap("v1", new ArithmeticExp('-',new ReadHeap("v1"), new ConstExpr(1))),
                                                new Unlock("x")

                                        )
                                )
                        ),//lock(x);wh(v1,rh(v1)+1);unlock(x)
                        new CompStmt(
                                new LockStmt("x"),
                                new CompStmt(
                                        new WriteHeap("v1", new ArithmeticExp('+',new ReadHeap("v1"),new ConstExpr(1))),
                                        new Unlock("x")
                                )
                        )
                )

        );
        Statement s3 = new NewLock("q");
        Statement s4 = new ForkStmt(
                new CompStmt(
                        new ForkStmt(
                                new CompStmt(
                                        new LockStmt("q"),
                                        new CompStmt(
                                                //wh(v2,rh(v2)+5);unlock(q)
                                                new WriteHeap("v2", new ArithmeticExp('+', new ConstExpr(5),new ReadHeap("v2"))),
                                                new Unlock("q")
                                        )

                                )

                        ),//m=100;lock(q);wh(v2,rh(v2)+1);unlock(q)
                        new CompStmt(
                                new AssignStmt("m", new ConstExpr(100)),
                                new CompStmt(
                                        new LockStmt("q"),
                                        new CompStmt(
                                                new WriteHeap("v2", new ArithmeticExp('+', new ReadHeap("v2"), new ConstExpr(1))),
                                                new Unlock("q")
                                        )
                                )
                        )

                )
        );
        //z=200;z=300;z=400;
        Statement s5 = new CompStmt(
                new AssignStmt("z", new ConstExpr(200)),
                new CompStmt(
                        new AssignStmt("z", new ConstExpr(300)),
                        new AssignStmt("z", new ConstExpr(400))
                        )
        );
        //lock(x);
        //print (rh(v1));
        Statement s6 = new CompStmt(
                new LockStmt("x"),
                new PrintStmt(
                        new ReadHeap("v1")
                )
        );
        //unlock(x);
        //lock(q);
        //print(rh(v2));
        Statement s7 = new CompStmt(
                new Unlock("x"),
                new CompStmt(
                        new LockStmt("q"),
                        new PrintStmt(new ReadHeap("v2"))
                )
        );
        //unlock(q)
        Statement s8 = new Unlock("q");
        Statement finalStatement = new CompStmt(
                s1, new CompStmt(s2, new CompStmt(
                        s3, new CompStmt(s4, new CompStmt( s5, new CompStmt(s6,
                new CompStmt(s7, s8))))
        ))
        );
        return finalStatement;
    }

    public GUI(){

    }

    public static ObservableList<HeapTableViewStuff> getHeap(MyHeapInterface<Integer, Integer> heap ){
        List<HeapTableViewStuff> finalList = heap.getMap().entrySet().stream().map(e->new HeapTableViewStuff(
                e.getKey(), e.getValue())).collect(Collectors.toList());
        return FXCollections.observableArrayList(finalList);
    }

    public static ObservableList<SymTableViewStuff> getSymTable(MyDictionaryInterface<String, Integer> symTable){
        List<SymTableViewStuff> finalList = symTable.getContent().entrySet().stream().map(
                e->new SymTableViewStuff(e.getKey(), e.getValue())).collect(Collectors.toList());
        return FXCollections.observableArrayList(finalList);

    }

    private static ObservableList<FileTableViewStuff> getFileTable(FileTableInterface<Integer, FileData> fileTable){
        List<FileTableViewStuff> finalList = fileTable.getContent().entrySet().stream().map(
                e->new FileTableViewStuff(e.getKey(), e.getValue().getFilename())
        ).collect(Collectors.toList());
        return FXCollections.observableArrayList(finalList);
    }

    public static ObservableList<LatchTableStuff> getLatchTable(LatchTableInterface<Integer, Integer> latch){
        List<LatchTableStuff> finalList = latch.getContent().entrySet().stream().map(e->new LatchTableStuff(
                e.getKey(), e.getValue())).collect(Collectors.toList());
        return FXCollections.observableArrayList(finalList);
    }

    public static ObservableList<LockTableStuff> getLockTable(LockTableInterface<Integer, Integer> lock){
        List<LockTableStuff> finalList = lock.getContent().entrySet().stream().map(e->new LockTableStuff(
                e.getKey(), e.getValue())).collect(Collectors.toList());
        return FXCollections.observableArrayList(finalList);
    }

    public static ObservableList<BarrierTableStuff> getBarrier(CyclicBarrierInterface<Integer, BarrierPair<List<Integer>, Integer>> barrier ){
        List<BarrierTableStuff> finalList = barrier.getContent().entrySet().stream().map(e->new BarrierTableStuff(
                e.getKey(), e.getValue().getSecond(), e.getValue().getSecond().toString())).collect(Collectors.toList());
        return FXCollections.observableArrayList(finalList);
    }

    @SuppressWarnings("unchecked")
    private static void fillAll(TableView heapv, TableView filev, TableView symbv, TableView barrierv, TableView latchv,
                                TableView lockv,
                                ListView<Integer> outv, ListView<Statement> execv, ListView<PrgState> prgv,
                                       MyHeapInterface<Integer, Integer> heap, FileTableInterface<Integer,
            FileData> file, MyDictionaryInterface<String, Integer> symb, MyListInterface<Integer> out,
                                       MyStackInterface<Statement> exec, List<PrgState> prg,
                                CyclicBarrier<Integer, BarrierPair<List<Integer>, Integer>> barrier
                                , LatchTableInterface<Integer, Integer> latchTable, LockTableInterface<Integer,Integer> lockTable) {
        heapv.setItems(getHeap(heap));
        latchv.setItems(getLatchTable(latchTable));
        lockv.setItems(getLockTable(lockTable));

        filev.setItems(getFileTable(file));
        symbv.setItems(getSymTable(symb));
        barrierv.setItems(getBarrier(barrier));

        ObservableList outl = FXCollections.observableArrayList();
        outl.addAll(out.getCollection());
        outv.setItems(outl);

        ObservableList execl = FXCollections.observableArrayList();
        execl.addAll(exec.getCollection());
        execv.setItems(execl);

        ObservableList prgl = FXCollections.observableArrayList();
        prgl.addAll(prg);
        prgv.setItems(prgl);
    }
    @SuppressWarnings("unchecked")
    public static void runProgram(Statement s){
        MyStackInterface<Statement> stack = new MyStack<>();
        stack.push(s);
        PrgState ps = new PrgState(stack, new MyDictionary<>(), new MyList<>(), s,  new FileTable<>(), new MyHeap<>(), new CyclicBarrier<>(), new LatchTable<>() , new LockTable<>() );
        RepositoryInterface repo = new Repository("logGui");
        repo.addPrgState(ps);
        Controller ctrl = new Controller(repo);

        //heap table
        Label heapLbl = new Label("Heap");
        heapLbl.setFont(new Font("Verdana", 20));
        heapLbl.setAlignment(Pos.CENTER);
        heapLbl.setPrefSize(1000, 30);
        TableView heapTable = new TableView();
        TableColumn address = new TableColumn("Address");
        address.setCellValueFactory(new PropertyValueFactory<HeapTableViewStuff, String>("address"));
        address.setPrefWidth(124);
        TableColumn value = new TableColumn("Value");
        value.setCellValueFactory(new PropertyValueFactory<HeapTableViewStuff, String>("value"));
        value.setPrefWidth(124);
        heapTable.getColumns().addAll(address, value);
        VBox heapV = new VBox(heapLbl, heapTable);
        heapV.setPrefSize(250, 200);

        //prepare file table
        Label fileLbl = new Label("File table");
        fileLbl.setFont(new Font("Verdana", 20));
        fileLbl.setAlignment(Pos.CENTER);
        fileLbl.setPrefSize(1000, 30);
        TableView fileTable = new TableView();
        TableColumn id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<FileTableViewStuff, String>("id"));
        id.setPrefWidth(124);
        TableColumn filename = new TableColumn("File name");
        filename.setCellValueFactory(new PropertyValueFactory<FileTableViewStuff, String>("filename"));
        filename.setPrefWidth(124);
        fileTable.getColumns().addAll(id, filename);
        VBox fileV = new VBox(fileLbl, fileTable);
        fileV.setPrefSize(250, 200);

        //prepare symbol table
        Label symbolLbl = new Label("Symbol table");
        symbolLbl.setFont(new Font("Verdana", 20));
        symbolLbl.setAlignment(Pos.CENTER);
        symbolLbl.setPrefSize(1000, 30);
        TableView symbolTable = new TableView();

        TableColumn varName = new TableColumn("Variable Name");
        varName.setCellValueFactory(new PropertyValueFactory<SymTableViewStuff, String>("varName"));
        varName.setPrefWidth(124);
        TableColumn valueST = new TableColumn("Value");
        valueST.setCellValueFactory(new PropertyValueFactory<SymTableViewStuff, String>("value"));
        valueST.setPrefWidth(124);
        symbolTable.getColumns().addAll(varName, valueST);
        VBox symbolV = new VBox(symbolLbl, symbolTable);
        symbolV.setPrefSize(250, 300);

        //prepare latch table
        Label latchLabel = new Label("Latch Table");
        latchLabel.setFont(new Font("Verdana", 20));
        latchLabel.setAlignment(Pos.CENTER);
        latchLabel.setPrefSize(1000, 30);

        TableView latchTable = new TableView();
        TableColumn latchLocation = new TableColumn("Location");
        latchLocation.setCellValueFactory(new PropertyValueFactory<LatchTableStuff, Integer>("latchLocation"));
        latchLocation.setPrefWidth(124);
        TableColumn latchValue = new TableColumn("Value");
        latchValue.setCellValueFactory(new PropertyValueFactory<LatchTableStuff, String>("latchValue"));
        latchValue.setPrefWidth(124);
        latchTable.getColumns().addAll(latchLocation, latchValue);
        VBox latchView = new VBox(latchLabel, latchTable);
        latchView.setPrefSize(250, 300);

        //prepare lock table
        Label lockLabel = new Label("Lock Table");
        lockLabel.setFont(new Font("Verdana", 20));
        lockLabel.setAlignment(Pos.CENTER);
        lockLabel.setPrefSize(1000, 30);

        TableView lockTable = new TableView();
        TableColumn lockLocation = new TableColumn("Location");
        lockLocation.setCellValueFactory(new PropertyValueFactory<LockTableStuff, Integer>("lockLocation"));
        lockLocation.setPrefWidth(124);
        TableColumn lockValue = new TableColumn("Value");
        lockValue.setCellValueFactory(new PropertyValueFactory<LockTableStuff, String>("lockValue"));
        lockValue.setPrefWidth(124);
        lockTable.getColumns().addAll(lockLocation, lockValue);
        VBox lockView = new VBox(lockLabel, lockTable);
        lockView.setPrefSize(250, 300);

        //prepare barrier table
        Label barrierLabel = new Label("Cyclic Barrier: ");
        barrierLabel.setFont(new Font("Verdana", 20));
        barrierLabel.setAlignment(Pos.CENTER);
        barrierLabel.setPrefSize(1000, 30);
        TableView barrierTable = new TableView();

        TableColumn barrierLocation = new TableColumn("BarrierLocation");
        barrierLocation.setCellValueFactory(new PropertyValueFactory<BarrierTableStuff, Integer>("barrierLocation"));
        barrierLocation.setPrefWidth(124);

        TableColumn barrierValue = new TableColumn("Value");
        barrierValue.setCellValueFactory(new PropertyValueFactory<BarrierTableStuff, Integer>("barrierValue"));
        barrierValue.setPrefWidth(124);

        TableColumn list = new TableColumn("List");
        list.setCellValueFactory(new PropertyValueFactory<BarrierTableStuff, Integer>("list"));
        list.setPrefWidth(124);

        barrierTable.getColumns().addAll(barrierLocation, barrierValue, list);
        VBox barrierV = new VBox(barrierLabel, barrierTable);
        barrierV.setPrefSize(250, 300);

        //prepare output list
        Label outputLbl = new Label("Output");
        outputLbl.setFont(new Font("Verdana", 20));
        outputLbl.setAlignment(Pos.CENTER);
        outputLbl.setPrefSize(1000, 30);
        ListView<Integer> outputList = new ListView<>();
        outputList.setPrefSize(250, 300);
        VBox outputV = new VBox(outputLbl, outputList);
        outputV.setPrefSize(250, 200);


        //prepare execution stack list
        Label execLbl = new Label("Execution stack");
        execLbl.setFont(new Font("Verdana", 20));
        execLbl.setAlignment(Pos.CENTER);
        execLbl.setPrefSize(1000, 30);
        ListView<Statement> execList = new ListView<>();
        execList.setPrefSize(250, 300);
        VBox execV = new VBox(execLbl, execList);
        execV.setPrefSize(250, 300);


        //prepare prgstate list
        Label prgLbl = new Label("Program states");
        prgLbl.setFont(new Font("Verdana", 20));
        prgLbl.setAlignment(Pos.CENTER);
        prgLbl.setPrefSize(1000, 30);

        Label prgCount = new Label("Current nr: 1");
        prgCount.setFont(new Font("Verdana", 15));
        prgCount.setAlignment(Pos.CENTER);
        prgCount.setPrefSize(1000, 30);

        ListView<PrgState> prgList = new ListView<>();
        prgList.setPrefSize(250, 580);
        VBox prgV = new VBox(prgLbl, prgCount, prgList );
        prgV.setPrefSize(250, 600);

        prgList.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            PrgState p = ctrl.getRepo().getPrgList().get(prgList.getSelectionModel().getSelectedIndex());
            fillAll(heapTable, fileTable, symbolTable, barrierTable,latchTable, lockTable, outputList, execList, prgList,
                    p.getHeap(), p.getFileTable(), p.getSymTable(), p.getMessages(), p.getExecStack(),
                    ctrl.getRepo().getPrgList(), p.getCyclicBarrier(), p.getLatchTable(), p.getLockTable());
        });
        prgList.getSelectionModel().select(0);

        //prepare the one step button
        Button oneStep = new Button("One Step");

        oneStep.setPrefSize(100, 50);
        oneStep.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            try {
                if (prgList.getSelectionModel().getSelectedIndex() < 0)
                    prgList.getSelectionModel().select(0);
                PrgState p = ctrl.getRepo().getPrgList().get(prgList.getSelectionModel().getSelectedIndex());
                ctrl.oneStepForAllPrg(ctrl.getRepo().getPrgList());
                fillAll(heapTable, fileTable, symbolTable, barrierTable,latchTable, lockTable, outputList, execList, prgList,
                        p.getHeap(), p.getFileTable(), p.getSymTable(), p.getMessages(), p.getExecStack(),
                        ctrl.getRepo().getPrgList(), p.getCyclicBarrier(), p.getLatchTable(), p.getLockTable());
                prgCount.setText("Current nr: " + ctrl.getRepo().getPrgList().size());
            } catch (InterruptedException e) {
                Label error = new Label(e.getMessage());
                error.setPrefSize(200, 60);
                Stage stage = new Stage();
                stage.setTitle("Error! ");
                stage.setScene(new Scene(error));
                stage.show();
            }
        });

        //prepare the actual view
        VBox hfo = new VBox(heapV, fileV, outputV);
        VBox symExec = new VBox(symbolV, execV);
        HBox info = new HBox(prgV, symExec, hfo, latchView, barrierV, lockView);

        Stage stage = new Stage();
        Button mainExit = new Button("Exit");
        mainExit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> stage.close());
        mainExit.setPrefSize(100, 50);
        HBox buttons = new HBox(oneStep, mainExit);
        buttons.setAlignment(Pos.CENTER);
        VBox view = new VBox(info, buttons);
        view.setAlignment(Pos.CENTER);

        stage.setTitle("Java Toy Language Interpreter");
        Scene newScene = new Scene(view);
        newScene.getStylesheets().add
                (GUI.class.getResource("GUI.css").toExternalForm());

        stage.setScene(newScene);

        stage.show();
        PrgState p = ctrl.getRepo().getPrgList().get(0);
        fillAll(heapTable, fileTable, symbolTable, barrierTable,latchTable, lockTable, outputList, execList, prgList,
                p.getHeap(), p.getFileTable(), p.getSymTable(), p.getMessages(), p.getExecStack(),
                ctrl.getRepo().getPrgList(), p.getCyclicBarrier(), p.getLatchTable(), p.getLockTable());

        prgList.getSelectionModel().select(0);
    }

    public static List<Statement> getStatements() {
        List<Statement> statements = new ArrayList<>();

        Statement stmt1 =
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

        Statement stmt2 = new CompStmt(
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

        Statement stmt3 = new CompStmt(
                new AssignStmt("a", new ConstExpr(5)),
                new CompStmt(
                        new IfStmt(
                                new ArithmeticExp(
                                        '-',
                                        new VarExp("a"),
                                        new ConstExpr(5)
                                ),
                                new PrintStmt(new ConstExpr(1)),
                                new PrintStmt(new ConstExpr(2))
                        ),
                        new PrintStmt(
                                new ArithmeticExp(
                                        '*',
                                        new ArithmeticExp(
                                                '+',
                                                new ConstExpr(5),
                                                new ConstExpr(7)
                                        ),
                                        new VarExp("a")
                                )
                        )
                )
        );

        //new(v1,2);new(v2,3);new(v3,4);newBarrier(cnt,rH(v3));
        //fork(wh(v1,rh(v1)*10));print(rh(v1);await(cnt)));
//        fork(await(cnt);wh(v2,rh(v2)*10));print(rh(v2)));
//        fork(await(cnt);wh(v3,rh(v3)*10));wh(v3,rh(v3)*10));print(rh(v3)));
//        await(cnt);
//        print(2000);
        Statement stmt4 = new CompStmt(
                new CompStmt(
                        new AssignStmt("v1", new ConstExpr(2)),
                        new CompStmt(new AssignStmt("v2", new ConstExpr(3)), new AssignStmt("v3", new ConstExpr(4)))),
                new CompStmt(
                        new CompStmt(
                            new ForkStmt(new WriteHeap(
                                    "v1", new ArithmeticExp('*', new ReadHeap("v1"), new ConstExpr(10))
                                    )
                            ),
                            new PrintStmt(new ReadHeap("v1"))
                        ),new Await("cnt")
                )
        );
        /* The following program must be hard coded in your implementation:
        v=10;
        (fork(v=v-1;v=v-1;print(v)); sleep(10);print(v*10) */
        Statement stmt5 = new CompStmt(new CompStmt(new AssignStmt("v", new ConstExpr(10)),
                                        new CompStmt(
                                            new ForkStmt(new CompStmt(new CompStmt( new DecrementStmt("v"),new DecrementStmt("v")),
                                                                new PrintStmt(new VarExp("v")))),
                                            new Sleep(10))),
                                        new PrintStmt(new ArithmeticExp('*', new VarExp("v"), new ConstExpr(10))));

        /*
        * v=0;
        (while(v<3) (fork(print(v);v=v+1);v=v+1);
        sleep(15);
        print(v*10)*/

        Statement stmt6 =
                new CompStmt(
                    new AssignStmt("v", new ConstExpr(0)),
                    new CompStmt(
                            new WhileStmt(
                                new BooleanExpr(new VarExp("v"), "<", new ConstExpr(3)),
                                new CompStmt(
                                    new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")), new IncrementStmt("v"))),
                                    new IncrementStmt("v")
                                    )),
                            new CompStmt(new Sleep(10), new PrintStmt(new ArithmeticExp('*', new VarExp("v"), new ConstExpr(10))))));

        /*v=0;
        (repeat (fork(print(v);v=v-1);v=v+1) until v==3);
        x=1;y=2;z=3;w=4;
        print(v*10)
        The final Out should be {0,1,2,30}*/

        Statement stmt7 =
                new CompStmt(
                    new AssignStmt("v", new ConstExpr(0)),
                    new CompStmt(
                            new RepeatUntilStmt(
                                    new CompStmt(
                                            new ForkStmt(
                                                new CompStmt(
                                                    new PrintStmt(new VarExp("v")),
                                                    new DecrementStmt("v")
                                                )
                                            ),
                                            new IncrementStmt("v")
                                    ),
                                    new BooleanExpr(new VarExp("v"), "==", new ConstExpr(3))
                            ),
                            new CompStmt(
                                    new AssignStmt("x", new ConstExpr(1)),
                                    new CompStmt(
                                            new AssignStmt("y", new ConstExpr(2)),
                                            new CompStmt(
                                                    new AssignStmt("w", new ConstExpr(4)),
                                                    new PrintStmt(
                                                            new ArithmeticExp('*', new VarExp("v"), new ConstExpr(10)))
                                            )
                                    )
                            )
                    )
                );

        /*new(v1,2);--
        new(v2,3);--
        new(v3,4);--
        newLatch(cnt,rH(v2));---
        fork(wh(v1,rh(v1)*10));--
        print(rh(v1));--
        countDown(cnt);--
        fork(wh(v2,rh(v2)*10));--
        print(rh(v2));--
        countDown(cnt);--
        fork(wh(v3,rh(v3)*10));--
        print(rh(v3));--
        countDown(cnt))));--
        await(cnt);--
        print(100);--
        countDown(cnt);--
        print(100)*/

        Statement stmt8 = new CompStmt(
                new NewHeap("v1", new ConstExpr(2)),
                new CompStmt(
                        new NewHeap("v2", new ConstExpr(3)),
                        new CompStmt(
                                new NewHeap("v3", new ConstExpr(4)),
                                new CompStmt(
                                        new NewLatch("cnt", new ReadHeap("v2")),
                                        new CompStmt(
                                                new ForkStmt(new WriteHeap("v1", new ArithmeticExp('*', new ConstExpr(10), new ReadHeap("v1")))),
                                                new CompStmt(
                                                        new PrintStmt(new ReadHeap("v1")),
                                                        new CompStmt(
                                                             new CountDown("cnt"),
                                                             //fork(wh(v2,rh(v2)*10))
                                                             new CompStmt(
                                                                     new ForkStmt(new WriteHeap("v2", new ArithmeticExp('*', new ConstExpr(10), new ReadHeap("v2")))),
                                                                     //print(rh(v2));
                                                                     new CompStmt(
                                                                             new PrintStmt(new ReadHeap("v2")),
                                                                             new CompStmt(
                                                                                     new CountDown("cnt"),
                                                                                     new CompStmt(
                                                                                             //fork(wh(v3,rh(v3)*10));
                                                                                             new ForkStmt(new WriteHeap("v3", new ArithmeticExp('*', new ConstExpr(10), new ReadHeap("v3")))),
                                                                                             new CompStmt(
                                                                                                     new PrintStmt(new ReadHeap("v3")),
                                                                                                     new CompStmt(
                                                                                                             new CountDown("cnt"),
                                                                                                             new CompStmt(
                                                                                                                     new LatchAwait("cnt"),
                                                                                                                     new CompStmt(
                                                                                                                             new PrintStmt(new ConstExpr(100)),
                                                                                                                             new CompStmt(
                                                                                                                                     new CountDown("cnt"),
                                                                                                                                     new PrintStmt(new ConstExpr(100))


                                                                                                                             )
                                                                                                                     )
                                                                                                             )

                                                                                                     )


                                                                                             )
                                                                                     )
                                                                             )

                                                                     )

                                                             )
                                                        )
                                                )
                                        )

                                )


                                )
                )
        );

        /*new(v1,2);--
        new(v2,3);--
        new(v3,4);--
        newBarrier(cnt,rH(v3));--
        fork(wh(v1,rh(v1)*10));--
        print(rh(v1);--
        await(cnt)));--
        fork(await(cnt);--
        wh(v2,rh(v2)*10));--
        print(rh(v2)));--
        fork(await(cnt);--
        wh(v3,rh(v3)*10));--
        wh(v3,rh(v3)*10));--
        print(rh(v3)));--
        await(cnt);--
        print(2000);*/



        Statement stmt9 = new CompStmt(
                new NewHeap("v1", new ConstExpr(2)),
                new CompStmt(
                        new NewHeap("v2", new ConstExpr(3)),
                        new CompStmt(
                                new NewHeap("v3", new ConstExpr(4)),
                                new CompStmt(
                                        //newBarrier(cnt,rH(v3));
                                        new NewBarrier("cnt", new ReadHeap("v3")),
                                        new CompStmt(
//                                                fork(wh(v1,rh(v1)*10));
                                                new ForkStmt(new WriteHeap("v1", new ArithmeticExp('*', new ConstExpr(10), new ReadHeap("v1")))),
                                                new CompStmt(
                                                        //print(rh(v1);
                                                        new PrintStmt(new ReadHeap("v1")),
                                                        new CompStmt(
                                                                new Await("cnt"),
                                                                new CompStmt(
                                                                        //fork(await(cnt);
                                                                        new ForkStmt(new Await("cnt")),
                                                                        new CompStmt(
                                                                                //wh(v2,rh(v2)*10));
                                                                                new WriteHeap("v2", new ArithmeticExp('*', new ConstExpr(10), new ReadHeap("v2"))),
                                                                                new CompStmt(
                                                                                      //print(rh(v2)));
                                                                                      new PrintStmt(new ReadHeap("v2")),
                                                                                      new CompStmt(
                                                                                              //fork(await(cnt);
                                                                                              new ForkStmt(new Await("cnt")),
                                                                                              new CompStmt(
                                                                                                      //wh(v3,rh(v3)*10));
                                                                                                      new WriteHeap("v3", new ArithmeticExp('*', new ConstExpr(10), new ReadHeap("v3"))),
                                                                                                      new CompStmt(
                                                                                                              //wh(v3,rh(v3)*10));
                                                                                                              new WriteHeap("v3", new ArithmeticExp('*', new ConstExpr(10), new ReadHeap("v3"))),
                                                                                                              new CompStmt(
                                                                                                                      //print(rh(v3)));
                                                                                                                      new PrintStmt(new ReadHeap("v3")),
                                                                                                                      new CompStmt(
                                                                                                                              //await(cnt);
                                                                                                                              new Await("cnt"),
                                                                                                                              new PrintStmt(new ConstExpr(2000))
                                                                                                                      )

                                                                                                              )

                                                                                                      )
                                                                                              )
                                                                                      )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )

                                        )
                                )
                        )
                )
        );

        /*v=20;
        (for(v=0;v<3;v=v+1) fork(print(v);v=v+1) );
        print(v*10) */

        Statement stmt12 =
                new CompStmt(
                    new AssignStmt("v", new ConstExpr(20)),
                    new CompStmt(
                        new ForStmt(new ConstExpr(0),
                                new BooleanExpr(new VarExp("v"), "<", new ConstExpr(3)),
                                new ArithmeticExp('+', new ConstExpr(1), new VarExp("v")),
                                new ForkStmt(
                                        new CompStmt(
                                        new PrintStmt(new VarExp("v")),
                                        new AssignStmt("v", new ArithmeticExp('+', new ConstExpr(1), new VarExp("v"))))
                                )
                        ), new PrintStmt(new ArithmeticExp('*', new ConstExpr(10), new VarExp("v")))
                    )
                );

        //stmt 9 dar scris altfel
        Statement stmt10 = getStmt9();
        Statement stmt11 = getStmt10();

        statements.add(stmt1);
        statements.add(stmt2);
        statements.add(stmt3);
        //statements.add(stmt4);
        statements.add(stmt5);
        statements.add(stmt6);
        statements.add(stmt7);
        statements.add(stmt8);
        statements.add(stmt9);
        statements.add(stmt10);
        statements.add(stmt11);
        statements.add(stmt12);

        return statements;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //initialize the list with programs
        List<Statement> programList = getStatements();

        //create the ListView with programs
        ListView<Statement> list = new ListView<>();
        for (Statement s : programList)
            list.getItems().add(s);
        list.setPrefSize(750, 300);

        //create exit button
        Button exitButton = new Button("Exit");
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> primaryStage.close());
        exitButton.setPrefSize(1500, 20);


        //create select button
        Button selectButton = new Button("Select");
        selectButton.setPrefSize(1500, 20);
        selectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> GUI.runProgram(list.getSelectionModel().getSelectedItem()));

        //create a horizontal box which will hold the buttons
        HBox buttons = new HBox(selectButton, exitButton);
        buttons.setPrefSize(750, 20);

        //create a vertical box which will hold everything
        VBox all = new VBox(list, buttons);

        list.getSelectionModel().select(0);

        Scene scene = new Scene(all);
        scene.getStylesheets().add
                (GUI.class.getResource("GUI.css").toExternalForm());
        primaryStage.setTitle("Select a program");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); //an object Application is created
    }
}
