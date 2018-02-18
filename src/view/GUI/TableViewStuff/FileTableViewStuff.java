package view.GUI.TableViewStuff;

public class FileTableViewStuff {
    public  Integer id;
    public String name;

    public FileTableViewStuff(Integer i, String n){
        id = i; name = n;

    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

