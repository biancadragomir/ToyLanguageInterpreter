package view.GUI.TableViewStuff;

public class SymTableViewStuff {
    public String varName;
    public Integer value;

    public SymTableViewStuff(String n, Integer v){
        varName = n; value = v;
    }

    public Integer getValue() {
        return value;
    }

    public String getVarName() {
        return varName;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setName(String name) {
        this.varName = name;
    }
}
