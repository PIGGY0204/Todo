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
            System.out.print("command:");
            String command = input.next();
            int n;
            switch (command) {
                case "add":
                    todoList.add(new TheTask(input.nextLine()));
                    printList(todoList, doneList);
                    break;
                case "save":
                    saveList(todoFile, todoList);
                    saveList(doneFile, doneList);
                    System.out.println("save success!");
                    break;
                case "print":
                    printList(todoList, doneList);
                    break;
                case "move":
	            todoList.move(input.nextInt(), input.nextInt());
                    printList(todoList, doneList);
                    break;
                case "clean":
                    doneList.clean();
                    printList(todoList, doneList);
                    break;
                case "remove":
                    n = input.nextInt();
                    if (n < todoList.getSize())
                        todoList.remove(n);
                    else
                        doneList.remove(n - todoList.getSize());
                    printList(todoList, doneList);
                    break;
                case "change":
                    n = input.nextInt();
                    if (n < todoList.getSize()) {
                        TheTask current = todoList.remove(n);
                        doneList.add(current);
                    } else {
                        TheTask current = doneList.remove(n - todoList.getSize());
                        todoList.add(current);
                    }
                    printList(todoList, doneList);
                    break;
                case "exit":
                    saveList(todoFile, todoList);
                    saveList(doneFile, doneList);
                    System.exit(0);
                default:
                    break;
            }   
        }
    }

    public static void printList(TodoLinkedList todoList, TodoLinkedList doneList) {
        int i = 0;

        System.out.format("  %s %-40s%s\n", "CHECK", "TASK", "TIME");
        for (TheTask e: todoList)
            System.out.format("%02d [ ] %-40s %s\n", i++, e.getName(), e.getDate());

        for (int n = 0; n < 76; n++)
            System.out.print("=");
        System.out.println("");

        for (TheTask e: doneList)
            System.out.format("%02d [x] %-40s %s\n", i++, e.getName(), e.getDate());
        
        System.out.println("");
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
