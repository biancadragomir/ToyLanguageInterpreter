package view.GUI.TableViewStuff;

public class HeapTableViewStuff {
    public Integer address; public Integer value;

    public Integer getValue() {
        return value;
    }

    public Integer getAddress() {
        return address;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public HeapTableViewStuff(Integer a, Integer v){
        address = a; value =v ;
    }
}
