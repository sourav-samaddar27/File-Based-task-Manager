package com.example.taskmanager;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.SortedMap;
import java.util.stream.Collectors;

class TaskManager {
    private List<Task> tasks;
    private final String filename;

    public TaskManager(String filename){
        this.filename=filename;
        this.tasks=new ArrayList<>();
        loadTasksFromFile();

    }
    private void loadTasksFromFile(){
        File file=new File(filename);
        if(!file.exists()){
            System.out.println("Task file not found. Creating a new one: "+filename);
            try{
                file.createNewFile();
            }catch(IOException e){
                System.err.println("Error creating new file: "+e);
            }
            return;
        }
        try(BufferedReader reader=new BufferedReader(new FileReader(filename))){
            String line;
            while((line=reader.readLine())!=null){
                if(line.trim().isEmpty()){
                    continue;
                }
                try{
                    tasks.add(Task.fromFileString(line));

                }catch(IllegalArgumentException e){
                    System.err.println("Skipping malformed task entry: "+line+" - "+e);
                }
            }
            System.out.println("Tasks loaded successfully from "+filename);
        }catch(FileNotFoundException e){
            System.err.println("Error Task file "+ filename +" not found. "+ e);
        }catch(IOException e){
            System.err.println("Error reading tasks from file: "+e);
        }
    }
    public void saveTasksToFile(){
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(filename))){
            for(Task task: tasks){
                writer.write(task.toFileString());
                writer.newLine();
            }
            System.out.println("Tasks saved successfully to "+filename);
        }catch(IOException e){
            System.err.println("Error saving tasks to file: "+e);
        }
    }



    public void addTask(Task task){
        if(findTaskByName(task.getName()).isPresent()){
            System.out.println("Error: taks with name "+task.getName()+" already exists. Please choose different name.");
            return;
        }
        tasks.add(task);
        System.out.println("Task "+task.getName()+" added.");
        saveTasksToFile();
    }
    public List<Task> getAllTasks(){
        return new ArrayList<>(tasks);
    }


    public Optional<Task> findTaskByName(String taskName){
        return tasks.stream().filter(task->task.getName().equalsIgnoreCase(taskName)).findAny();

    }
    public boolean markTaskCompleted(String taskName){
        Optional<Task> taskOptional=findTaskByName(taskName);
        if(taskOptional.isPresent()){
            taskOptional.get().setCompleted(true);
            System.out.println("Task "+taskName+" marked as completed.");
            saveTasksToFile();
            return true;
        }else{
            System.out.println("Task "+taskName+" deleted.");
            return false;
        }


    }
    public boolean deleteTask(String taskName){
        boolean removed= tasks.removeIf(task->task.getName().equalsIgnoreCase(taskName));
        if(removed){
            System.out.println("Task "+taskName+" deleted.");
            saveTasksToFile();
        }
        else {
            System.out.println("Task "+taskName+" not found.");
        }
        return removed;
    }

    public List<Task> getPendingTasks(){
        return tasks.stream().filter(task->!task.isCompleted()).collect(Collectors.toList());
    }
}
