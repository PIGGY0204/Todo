import java.util.Date;
import java.util.Scanner;
import java.io.*;

public class Todo {
    //todo
    public static void main(String[] args) {
        TodoLinkedList todoList = new TodoLinkedList();
        TodoLinkedList doneList = new TodoLinkedList();
        String todoPathname = "data/main/todo.dat";
        String donePathname = "data/main/done.dat";
        File todoFile = new File(todoPathname);
        File doneFile = new File(donePathname);
        Scanner input = new Scanner(System.in);

        readList(todoFile, todoList);
        readList(doneFile, doneList);
        printList(todoList, doneList);

        while (true) {
            String command = input.next();
            switch (command) {
                case "add":
                    todoList.add(new TheTask(input.nextLine()));
                    break;
                case "save":
                    saveList(todoFile, todoList);
                    saveList(doneFile, doneList);
                    break;
                case "print":
                    printList(todoList, doneList);
                    break;
                case "exit":
                    System.exit(0);
                default:
                    break;
            }   
        }
    }

    public static void printList(TodoLinkedList todoList, TodoLinkedList doneList) {
        System.out.println("CHECK" + "\tTASK" + "\tTIME");

        System.out.println("todo:");
        for (TheTask e: todoList)
            System.out.println("[ ]\t" + e.getName() + "\t" + e.getDate());
        System.out.println("done:");
        for (TheTask e: doneList)
            System.out.println("[x]\t" + e.getName() + "\t" + e.getDate());
    }

    public static void readList(File file, TodoLinkedList list) {
        try {
            try (
                ObjectInputStream input =
                    new ObjectInputStream(new FileInputStream(file));
            ) {
                while (true) {
                    Date date = (Date)(input.readObject());
                    String name = input.readUTF();
                    list.add(new TheTask(date, name));
                }
            }
        } catch (EOFException ex) {
            return;
        } catch (ClassNotFoundException ex) {
            return;
        } catch (Exception ex) {
            return;
        } 
    }

    public static void saveList(File file, TodoLinkedList list) {
        try {
            try (
                ObjectOutputStream output =
                    new ObjectOutputStream(new FileOutputStream(file));
            ) {
                for (TheTask e: list) {
                    output.writeObject(e.getDate());
                    output.writeUTF(e.getName());
                }
            }
        } catch (IOException ex) {
            return;
        } catch (Exception ex) {
            return;
        }
    }
}
