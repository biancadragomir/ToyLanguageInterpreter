package view.commands;

import controller.Controller;

public class RunExample extends Command {
    private Controller ctrl;
    public RunExample(String key, String desc, Controller ctrl){
        super(key, desc);
        this.ctrl=ctrl;
    }
    @Override
    public void execute() {
            ctrl.executeAll();
    }
}