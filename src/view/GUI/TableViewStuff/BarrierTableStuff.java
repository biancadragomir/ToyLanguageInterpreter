package view.GUI.TableViewStuff;

public class BarrierTableStuff {
    public Integer barrierLocation;
    public Integer barrierValue;
    public String list;

    public BarrierTableStuff(Integer l, Integer v, String listt){
        barrierLocation = l; barrierValue = v; list = listt;
    }

    public Integer getBarrierValue() {
        return barrierValue;
    }

    public Integer getBarrierLocation() {
        return barrierLocation;
    }

    public String getList() {
        return list;
    }

    public void setValue(Integer value) {
        this.barrierValue = value;
    }

    public void setList(String list) {
        this.list = list;
    }

    public void setLocation(Integer location) {
        this.barrierLocation = location;
    }
}
