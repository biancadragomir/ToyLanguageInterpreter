package repository;

import model.PrgState;

import java.util.List;

public interface RepositoryInterface {
    void logPrgStateExec(PrgState ps);
    void addPrgState(PrgState ps);
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> l);

}
