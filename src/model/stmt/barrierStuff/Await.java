package model.stmt.barrierStuff;

import model.PrgState;
import model.adt.BarrierPair;
import model.adt.MyDictionary;
import model.adt.interfaces.MyDictionaryInterface;
import model.stmt.Statement;

import java.util.ArrayList;
import java.util.List;

public class Await implements Statement {
    private String var;
    public Await(String var){
        this.var = var;//the var name from the sym table
        //mapped to a nr  into the barrier table
    }

    @Override
    public String toString(){
        return "await("+var+")";
    }

    @Override
    public PrgState execute(PrgState ps) {
        System.out.println(ps.getId()+" reached the barrier!");

        try{
            MyDictionaryInterface<String, Integer> symT= ps.getSymTable();
            if(!symT.contains(var)){
                throw new Exception("the barrier is not there! ");
            }
            else{
                int index = symT.get(var);
                if(!ps.getCyclicBarrier().contains(index))throw new Exception("barrier does not contain this index!");
                else{
                    //elseif BarrierTable[foundIndex]==(barrier_list,barrier_value) then

                    BarrierPair<List<Integer>, Integer> pair;
                    if(!ps.getCyclicBarrier().contains(index)) {
                        System.err.println("no entry like that in barrier table! ");
                        return null;
                    }
                    pair = ps.getCyclicBarrier().get(index);
                    //if length(barrier_list)==barrier_value then
                    if(pair.getFirst().size() == pair.getSecond() ){
                        System.out.println("same size barrier pair");
                        //do nothing
                    }
                    else{
                        //id of the current thread is in barrier_list
                        if(pair.getFirst().contains(ps.getId())){
                            ps.getExecStack().push(this);
                        }else{
                            ps.getExecStack().push(this);
                            //synchronized (ps.getCyclicBarrier()){
                                List<Integer> l =ps.getCyclicBarrier().get(index).getFirst();
                                l.add(ps.getId());
                                ps.getCyclicBarrier().update(index, new BarrierPair<>(l, ps.getCyclicBarrier().get(index).getSecond()));
                            //}
                        }
                    }
                }
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return null;
    }
}
