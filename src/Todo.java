import java.util.Date;
import java.util.Scanner;
import java.io.*;

public class Todo {
    //todo
    public static void main(String[] args) {
        TodoLinkedList todoList = new TodoLinkedList();
        TodoLinkedList doneList = new TodoLinkedList();
        String listName = "main";
        String listPathname = "data/" + listName;
        String todoPathname = listPathname + "/todo.dat";
        String donePathname = listPathname + "/done.dat";
        File todoFile = new File(todoPathname);
        File doneFile = new File(donePathname);
        File dataFile = new File("data");
        Scanner input = new Scanner(System.in);

        readList(todoFile, todoList);
        readList(doneFile, doneList);
        printList(todoList, doneList);

        while (true) {
            System.out.print(listName + ":");
            String command = input.next();
            int n;
            switch (command) {
                case "add":
                    todoList.add(new TheTask(input.nextLine().trim()));
                    printList(todoList, doneList);
                    break;
                case "save":
                    saveList(todoFile, todoList);
                    saveList(doneFile, doneList);
                    System.out.println("save success!");
                    break;
                case "ls":
                    printList(todoList, doneList);
                    break;
                case "mv":
	            todoList.move(input.nextInt(), input.nextInt());
                    printList(todoList, doneList);
                    break;
                case "clean":
                    doneList.clean();
                    printList(todoList, doneList);
                    break;
                case "rm":
                    n = input.nextInt();
                    if (n < todoList.getSize())
                        todoList.remove(n);
                    else
                        doneList.remove(n - todoList.getSize());
                    printList(todoList, doneList);
                    break;
                case "ck":
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
                case "mkls":
                    listName = input.nextLine().trim();
                    listPathname = "data/" + listName;
                    File listFile = new File(listPathname);
                    listFile.mkdirs();
                    todoPathname = listPathname + "/todo.dat";
                    donePathname = listPathname + "/done.dat";
                    todoFile = new File(todoPathname);
                    doneFile = new File(donePathname);

                    try {
                        todoFile.createNewFile();
                        doneFile.createNewFile();
                    } catch (IOException ex) {
                        System.out.println("make new list fail");
                    }

                    readList(todoFile, todoList);
                    readList(doneFile, doneList);
                    printList(todoList, doneList);
                    break;
                case "cg":
                    saveList(todoFile, todoList);
                    saveList(doneFile, doneList);
                    listName = input.nextLine().trim();
                    todoFile = new File("data/" + listName + "/todo.dat");
                    doneFile = new File("data/" + listName + "/done.dat");
                    readList(todoFile, todoList);
                    readList(doneFile, doneList);
                    printList(todoList, doneList);
                    break;
                case "lsls":
                    String[] allTodoList = dataFile.list();
                    int i = 0;
                    for (String s: allTodoList)
                        System.out.format("%02d %s\n", i++, s);
                    break;
                case "rmls":
                    File[] allTodoListFile = dataFile.listFiles();
                    File rmls = allTodoListFile[input.nextInt()];
                    (new File(rmls, "todo.dat")).delete();
                    (new File(rmls, "done.dat")).delete();
                    if (rmls.delete())
                        System.out.println("success!");
                    break;
                case "q":
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

        System.out.format("  %s %-40s %s\n", "CHECK", "TASK", "TIME");
        for (TheTask e: todoList)
            System.out.format("%02d [ ]  %-40s %s\n", i++, e.getName(), e.getDate());

        for (int n = 0; n < 76; n++)
            System.out.print("=");
        System.out.println("");

        for (TheTask e: doneList)
            System.out.format("%02d [x]  %-40s %s\n", i++, e.getName(), e.getDate());
        
        System.out.println("");
    }

    public static void readList(File file, TodoLinkedList list) {
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
