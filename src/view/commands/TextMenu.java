package view.commands;

import model.adt.MyDictionary;
import model.adt.interfaces.MyDictionaryInterface;

import java.util.Scanner;

public class TextMenu {
    private MyDictionaryInterface<String, Command> commands;

    public TextMenu() {
        commands = new MyDictionary<>();
    }

    public void addCommand(Command c) {
        commands.add(c.getKey(), c);
    }

    private void printMenu() {
        for (Command com : commands.getValues()) {
            String line = String.format("%4s : %s", com.getKey(), com.getDescription());
            System.out.println(line);
        }
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.printf("Input the option: ");
            String key = scanner.nextLine();
            Command com = commands.get(key);
            if (com == null) {
                System.out.println("Invalid Option");
                continue;
            }
            com.execute();
        }
    }
}