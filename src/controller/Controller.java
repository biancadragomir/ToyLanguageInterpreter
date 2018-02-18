package controller;

import model.PrgState;
import repository.RepositoryInterface;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    public ExecutorService executor;
    private RepositoryInterface repo;


    public RepositoryInterface getRepo() {
        return repo;
    }

    public Controller(RepositoryInterface r) {
        repo = r;
    }

    public void executeAll() {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedProgram(repo.getPrgList());
        while(prgList.size() > 0){
            try{
                oneStepForAllPrg(prgList);
                prgList = removeCompletedProgram(repo.getPrgList());
            }
            catch(InterruptedException e){
                System.err.println(e.getMessage());
            }
        }
        executor.shutdownNow();
        repo.setPrgList(prgList);
    }

    public Map<Integer, Integer> conservativeGarbageCollector(Collection<Integer> symTableValues,
                                                              Map<Integer, Integer> heap) {
        return heap.entrySet().stream().filter(e -> symTableValues.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<PrgState> removeCompletedProgram(List<PrgState> l) {
        return l.stream().filter(p -> p.isNotCompleted()).collect(Collectors.toList ());
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException{
        prgList.forEach(prg -> repo.logPrgStateExec(prg));
        List<Callable<PrgState>> callList = prgList.stream()
                .map(p -> (Callable<PrgState>) p::oneStep)
                .collect(Collectors.toList());
        executor = Executors.newFixedThreadPool(2);
        try{
            List<PrgState> newPrograms = executor.invokeAll(callList).stream()
                    .map(future -> {
                        PrgState state = null;
                        try {
                            state = future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            System.err.println(e.getMessage());
                            e.printStackTrace();
                        }
                        return state;
                    })
                    .filter(p -> p != null)
                    .collect(Collectors.toList());

            prgList.addAll(newPrograms);
            prgList.forEach(prg ->repo.logPrgStateExec(prg));
            repo.setPrgList(prgList);
        }catch(Exception e){
            System.err.println("error int oneStepForAll: "+e.getMessage());
        }

    }
}