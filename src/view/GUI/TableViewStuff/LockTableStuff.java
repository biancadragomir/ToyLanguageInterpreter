package view.GUI.TableViewStuff;

public class LockTableStuff {
    public Integer lockLocation;
    public Integer lockValue;

    public LockTableStuff(Integer l, Integer v){
        lockLocation = l;
        lockValue = v;
    }

    public Integer getLockLocation() {
        return lockLocation;
    }

    public Integer getLockValue() {
        return lockValue;
    }

    public void setLockLocation(Integer lockLocation) {
        this.lockLocation = lockLocation;
    }

    public void setLockValue(Integer lockValue) {
        this.lockValue = lockValue;
    }
}
