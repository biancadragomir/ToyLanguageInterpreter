package view.GUI.TableViewStuff;

public class LatchTableStuff {
    public Integer latchLocation;
    public Integer latchValue;

    public LatchTableStuff(Integer n, Integer v){
        latchLocation = n; latchValue = v;
    }

    public Integer getLatchLocation() {
        return latchLocation;
    }

    public Integer getLatchValue() {
        return latchValue;
    }

    public void setLatchLocation(Integer latchLocation) {
        this.latchLocation = latchLocation;
    }

    public void setLatchValue(Integer latchValue) {
        this.latchValue = latchValue;
    }
}
