package model.stmt;

import model.PrgState;

public interface Statement {
    PrgState execute(PrgState ps);
}
