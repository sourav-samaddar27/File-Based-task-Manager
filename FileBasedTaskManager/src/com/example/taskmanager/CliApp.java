package com.example.taskmanager;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class CliApp {

    private TaskManager taskManager;
    private TaskReminder taskReminder;
    private Scanner scanner;
    private static final String TASK_FILE="tasks.txt";

    public CliApp(){
        this.taskManager=new TaskManager(TASK_FILE);
        this.taskReminder=new TaskReminder(taskManager);
        this.scanner=new Scanner(System.in);
    }
    private void displayMenu(){
        System.out.println("\n--- File-Based Task Manager");
        System.out.println("1. Add Task");
        System.out.println("2. List All Tasks");
        System.out.println("3. List Pending Tasks");
        System.out.println("4. Mark Task as Completed");
        System.out.println("5. Delete Task");
        System.out.println("6. Exit");
        System.out.println("Enter your choice: ");
    }

    public void run() {
        taskReminder.startReminderService();
        int choice;
        do {
            displayMenu();
            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        addTask();
                        break;
                    case 2:
                        listAllTasks();
                        break;
                    case 3:
                        listPendingTasks();
                        break;
                    case 4:
                        markTaskCompleted();
                        break;
                    case 5:
                        deleteTask();
                    case 6:
                        System.out.println("Exiting Task Manager. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                choice = 0;
            } catch (Exception e) {
                System.err.println("An unexpected error occured: " + e);
                choice = 0;
            }
        } while (choice != 6) ;
            taskReminder.startReminderService();
            scanner.close();
    }


            private void addTask(){
                System.out.println("Enter task name: ");
                String name=scanner.nextLine();
                System.out.println("Enter task description: ");
                String description = scanner.nextLine(); // Read task description.
                System.out.print("Enter due date and time (yyyy-MM-dd HH:mm): ");
                String dueDateString = scanner.nextLine();

                try{
                    LocalDateTime dueDate=LocalDateTime.parse(dueDateString,Task.FORMATTER);
                    Task newTask=new Task(name,description,dueDate);
                    taskManager.addTask(newTask);
                }catch (DateTimeParseException e){
                    System.out.println("Invalid date format. Please use yyyy-MM-dd HH:mm. Task not added.");
                }

            }
            private void listAllTasks(){
                List<Task> allTask=taskManager.getAllTasks();
                if(allTask.isEmpty()){
                    System.out.println("No task to display");
                    return;
                }
                System.out.println("\n--- ALL Tasks ---");
                for(int i=0;i<allTask.size();i++){
                    System.out.println("Task #"+(i+1));
                    System.out.println(allTask.get(i));
                }
                System.out.println("----------");
            }
            private void listPendingTasks(){
                List<Task> pendingTasks=taskManager.getPendingTasks();
                if(pendingTasks.isEmpty()){
                    System.out.println("No Pending tasks to dispaly.");
                    return;
                }
                System.out.println("\n--- Pending Tasks ---");
                for (int i=0;i<pendingTasks.size();i++){
                    System.out.println("Task #"+(i+1));
                    System.out.println(pendingTasks.get(i));
                }
                System.out.println("-----------");
            }

            private void markTaskCompleted(){
                System.out.println("Enter the name of the task to mark as completed: ");
                String taskName=scanner.nextLine();
                taskManager.markTaskCompleted(taskName);
            }

            private void deleteTask(){
                System.out.println("Enter the name of the task to delete: ");
                String taskName=scanner.nextLine();
                taskManager.deleteTask(taskName);
            }

    public static void main(String[] args) {
        CliApp app=new CliApp();
        app.run();
    }







}
