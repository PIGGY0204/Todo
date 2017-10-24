import java.util.Date;
import java.util.Scanner;
import java.io.*;

public class Todo {
    private static TodoLinkedList todoList = new TodoLinkedList();
    private static TodoLinkedList doneList = new TodoLinkedList();
    private static String listName = "main";
    private static File dataFile = new File("data");
    private static File listFile = new File(dataFile, listName);
    private static File todoFile = new File(listFile, "todo.dat");
    private static File doneFile = new File(listFile, "done.dat");

    public static void main(String[] args) {
        initial();
        printList();

        while (true) {
            System.out.print("\n" + listName + ":");
            command();
        }

    }

    private static void command() {
        Scanner input = new Scanner(System.in);
        String command = input.next();
        int n;

        switch (command) {
            case "add":
                todoList.add(new TheTask(input.nextLine().trim()));
                printList();
                break;
            case "save":
                save();
                System.out.println("save success!");
                break;
            case "ls":
                printList();
                break;
            case "mv":
	        todoList.move(input.nextInt(), input.nextInt());
                printList();
                break;
            case "clean":
                doneList.clean();
                printList();
                break;
            case "rm":
                remove(input.nextInt());
                printList();
                break;
            case "ck":
                check(input.nextInt());
                printList();
                break;
            case "mkls":
                save();
                String newListName = input.nextLine().trim();
                if (newListName.equals("")) {
                    System.out.print("Please enter the new list name(hit enter to cancel): ");
                    newListName = input.nextLine().trim();
                }
                if (newListName.equals("")) {
                    printList();
                    break;
                }
                makeNewList(newListName);
                initial();
                printList();
                break;
            case "cg":
                save();
                change(input.nextLine().trim());
                initial();
                printList();
                break;
            case "lsls":
                listList();
                break;
            case "rmls":
                removeList(input.nextInt());
                listList();
                break;
            case "q":
                save();
                System.exit(0);
            default:
                break;
        }
    }

    private static void printList() {
        int i = 0;

        System.out.println("");

        System.out.format("  %s %-40s %s\n", "CHECK", "TASK", "TIME");

        for (int n = 0; n < 77; n++)
            System.out.print("-");
        System.out.println("");

        for (TheTask e: todoList)
            System.out.format("%02d [ ]  %-40s %s\n", i++, e.getName(), e.getDate());

        for (int n = 0; n < 77; n++)
            System.out.print("-");
        System.out.println("");

        for (TheTask e: doneList)
            System.out.format("%02d [x]  %-40s %s\n", i++, e.getName(), e.getDate());
    }

    private static void initial() {
        readList(todoFile, todoList);
        readList(doneFile, doneList);
    }

    private static void readList(File file, TodoLinkedList list) {
        list.clean();
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

    private static void save() {
        saveList(todoFile, todoList);
        saveList(doneFile, doneList);
    }

    private static void saveList(File file, TodoLinkedList list) {
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

    private static void makeNewList(String name) {
        listName = name;
        listFile = new File(dataFile, listName);
        listFile.mkdirs();
        todoFile = new File(listFile, "todo.dat");
        doneFile = new File(listFile, "done.dat");

        try {
            todoFile.createNewFile();
            doneFile.createNewFile();
        } catch (IOException ex) {
            System.out.println("make new list fail");
        }
    }

    private static void listList() {
        String[] allTodoList = dataFile.list();
        int i = 0;
        for (String s: allTodoList)
            System.out.format("%02d %s\n", i++, s);
    }

    private static void remove(int n) {
        if (n < todoList.getSize())
            todoList.remove(n);
        else
            doneList.remove(n - todoList.getSize());
    }

    private static void check(int n) {
        if (n < todoList.getSize()) {
            TheTask current = todoList.remove(n);
            doneList.add(current);
        } else {
            TheTask current = doneList.remove(n - todoList.getSize());
            todoList.add(current);
        }
    }

    private static void change(String tempListName) {
        File tempFile = new File(dataFile, tempListName);
        if (!tempFile.exists()) {
            System.out.println("list does not exist!");
            return;
        }
        listName = tempListName;
        listFile = tempFile;
        todoFile = new File(listFile, "todo.dat");
        doneFile = new File(listFile, "done.dat");
    }

    private static void removeList(int n) {
        File[] allTodoListFile = dataFile.listFiles();
        File rmls = allTodoListFile[n];
        (new File(rmls, "todo.dat")).delete();
        (new File(rmls, "done.dat")).delete();
        if (rmls.delete())
            System.out.println("success!");
    }
}
