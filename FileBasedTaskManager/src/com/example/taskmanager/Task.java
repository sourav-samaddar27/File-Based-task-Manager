package com.example.taskmanager;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

 class Task  implements Serializable{
        private static final long serialVersionUDI=1L;
        private String name;
        private String description;
        private LocalDateTime dueDate;
        private boolean completed;

        public static final DateTimeFormatter FORMATTER=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        public Task(String name, String description, LocalDateTime dueDate){
            this.name=name;
            this.description=description;
            this.dueDate=dueDate;
            this.completed=false;
        }

        public Task(String name, String description,String dueDateString, String completedString){
            this.name=name;
            this.description=description;
            this.dueDate=LocalDateTime.parse(dueDateString,FORMATTER);
            this.completed=Boolean.parseBoolean(completedString);
        }

        public String getName(){
            return name;
        }
        public String getDescription(){
            return description;
        }
        public LocalDateTime getDueDate(){
            return dueDate;
        }
        public boolean isCompleted(){
            return completed;
        }

        public void setName(String name){
            this.name=name;
        }
        public void setDescription(String description){
            this.description=description;
        }
        public void setDueDate(LocalDateTime dueDate){
            this.dueDate=dueDate;
        }
        public void setCompleted(boolean completed){
            this.completed=completed;
        }

        public String toFileString(){
            return String.format("%s|%S|%s|%b", name, description, dueDate.format(FORMATTER),completed);
        }

        public static Task fromFileString(String fileString){
            String[] parts=fileString.split("\\|",4);
            if(parts.length!=4){
                throw new IllegalArgumentException("Invalid task string format: "+fileString);
            }
            try{
                String name=parts[0];
                String description=parts[1];
                String dueDateString=parts[2];
                String completedString=parts[3];
                return new Task(name, description,dueDateString,completedString);

            }catch(java.time.format.DateTimeParseException e){
                throw new IllegalArgumentException("Invalid date format in task string: "+fileString,e);
            }
        }
        @Override
        public String toString(){
            String status=completed ? "[COMPLETED]":"[PENDING]";
            return String.format("Name: %s\nDescription: %s\nDue Date: %s\nStatus: %s\n", name , description,dueDate.format(FORMATTER),status);
        }
        @Override
        public boolean equals(Object o){
            if(this==o) return true;
            if(o==null || getClass()!=o.getClass()) return false;
            Task task=(Task) o;
            return Objects.equals(name,task.name) && Objects.equals(description,task.description) && Objects.equals(dueDate,task.dueDate);
        }

        @Override
        public int hashCode(){
            return Objects.hash(name, description,dueDate);
        }


}
