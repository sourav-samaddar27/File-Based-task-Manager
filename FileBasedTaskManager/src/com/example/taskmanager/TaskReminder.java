package com.example.taskmanager;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit; // Imports ChronoUnit for calculating time differences (e.g., minutes between two LocalDateTime objects).
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
class TaskReminder {
     private final TaskManager taskManager;
     private final Timer timer;

     private static final long REMINDER_INTERVAL_MS=60*1000; //(e.g., 60 seconds * 1000 ms/sec = 1 minute).
     private static final long REMIND_BEFORE_MINUTES=10;

     public TaskReminder(TaskManager taskManager){
          this.taskManager=taskManager;
          this.timer=new Timer(true);
     }

     public void startReminderService(){
          // timer.scheduleAtFixedRate(TimerTask task, long delay, long period)
          timer.scheduleAtFixedRate(new TimerTask(){
               @Override
               public void run(){
                    checkAndRemind();
               }
          },0,REMINDER_INTERVAL_MS);
          System.out.println("Task Remainder service started. Checking every "+(REMINDER_INTERVAL_MS/1000)+" seconds");
     }

     public void stopRemainderService(){
          timer.cancel();
          System.out.println("Task Remainder service stopped.");
     }
     private void checkAndRemind(){
          LocalDateTime now=LocalDateTime.now();
          List<Task> pendingTasks=taskManager.getPendingTasks();
          for(Task task:pendingTasks){
               LocalDateTime dueDate=task.getDueDate();
               long minutesUnitDue=ChronoUnit.MINUTES.between(now,dueDate);

               if(minutesUnitDue>0 && minutesUnitDue<=REMIND_BEFORE_MINUTES){
                    System.out.println("\n*** REMINDER ***");
                    System.out.println("Task "+task.getName()+" is due in "+minutesUnitDue+" minutes!");
                    System.out.println("Description "+task.getDescription());
                    System.out.println("Due Date: "+dueDate.format(Task.FORMATTER));
                    System.out.println("***********\n");
               }else if(minutesUnitDue<=0 && !task.isCompleted()){
                    System.out.println("\n*** OVERDUE TASK ALERT ***");
                    System.out.println("Task "+task.getName()+" was due "+Math.abs(minutesUnitDue)+" Minutes");
                    System.out.println("Description: "+ task.getDescription());
                    System.out.println("Due Date: "+dueDate.format(Task.FORMATTER));
                    System.out.println("****************\n");
               }
          }
     }
}
