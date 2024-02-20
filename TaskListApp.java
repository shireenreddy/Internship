import java.util.ArrayList;
import java.util.Scanner;

public class TaskListApp {
    private static ArrayList<String> taskList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("Task List Application");
            System.out.println("1. Add Task");
            System.out.println("2. Remove Task");
            System.out.println("3. List Tasks");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
            
            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    removeTask();
                    break;
                case 3:
                    listTasks();
                    break;
                case 4:
                    running = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 4.");
            }
        }
        scanner.close();
    }

    private static void addTask() {
        System.out.print("Enter task to add: ");
        String task = scanner.nextLine();
        taskList.add(task);
        System.out.println("Task added successfully.");
    }

    private static void removeTask() {
        if (taskList.isEmpty()) {
            System.out.println("Task list is empty.");
            return;
        }
        System.out.println("Current Tasks:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((i + 1) + ". " + taskList.get(i));
        }
        System.out.print("Enter task number to remove: ");
        int taskNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
        if (taskNumber < 1 || taskNumber > taskList.size()) {
            System.out.println("Invalid task number!");
            return;
        }
        taskList.remove(taskNumber - 1);
        System.out.println("Task removed successfully.");
    }

    private static void listTasks() {
        if (taskList.isEmpty()) {
            System.out.println("Task list is empty.");
            return;
        }
        System.out.println("Tasks:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((i + 1) + ". " + taskList.get(i));
        }
    }
}
