import java.util.Date;
import java.util.Scanner;
import java.io.*;

public class Todo {
    //todo
    public static void main(String[] args) {
        TodoLinkedList todoList = new TodoLinkedList();
        TodoLinkedList doneList = new TodoLinkedList();
        File todoFile = new File("data/mainList/todo.dat");
        File doneFile = new File("data/mainList/done.dat");
        Scanner input = new Scanner(System.in);

        while (true) {
            String command = input.next();
            switch (command) {
                case "ls":
                    //list
                    break; 
            }   
        }
    }

    public void readList(File file, TodoLinkedList list)
        throws ClassNotFoundException, IOException {
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
        }
    }

    public void saveList(File file, TodoLinkedList list)
        throws IOException {
        try (
            ObjectOutputStream output =
                new ObjectOutputStream(new FileOutputStream(file));
        ) {
            for (TheTask e: list) {
                output.writeObject(e.getDate());
                output.writeUTF(e.getName());
            }
        }
    }
}
