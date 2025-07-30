# File-Based-task-Manager

File-Based Task Manager (Java CLI)
A simple command-line interface (CLI) application developed in Java to manage and store tasks persistently in a local file. This project demonstrates fundamental Java concepts such as file I/O, multithreading, and robust exception handling.

Key Features
Task Management: Add, list, mark as completed, and delete tasks.

File Persistence: All task data is saved to and loaded from a plain text file (tasks.txt), ensuring data is not lost upon application shutdown.

Background Reminders: A separate thread periodically checks for upcoming or overdue tasks and displays reminders in the console without interrupting user interaction.

Robust Error Handling: Gracefully handles common issues like FileNotFoundException, IOException during file operations, and DateTimeParseException or NumberFormatException for invalid user input.

CLI Interface: Interact with the application via a simple menu-driven command-line interface.

Technologies Used
Language: Java (Core Java)

File I/O: java.io.BufferedReader, java.io.BufferedWriter, java.io.FileReader, java.io.FileWriter

Date & Time: java.time.LocalDateTime, java.time.format.DateTimeFormatter

Concurrency: java.util.Timer, java.util.TimerTask

IDE: IntelliJ IDEA

Project Structure
FileBasedTaskManager/
├── src/
│   └── com/
│       └── example/
│           └── taskmanager/
│               ├── Task.java          # Represents a single task entity
│               ├── TaskManager.java   # Manages task collection, loads/saves to file
│               ├── TaskReminder.java  # Handles background reminders using multithreading
│               └── CliApp.java        # Main CLI application entry point
└── tasks.txt                          # (Automatically created) Stores your task data



In IntelliJ, open src/com/example/taskmanager/CliApp.java.

Right-click anywhere in the main method and select Run 'CliApp.main()'.

Alternatively, from your project root in the terminal:

java -cp out com.example.taskmanager.CliApp

Interact with the CLI:

Follow the on-screen menu instructions in your terminal to add, list, mark, or delete tasks.

Observe the background reminders appearing periodically.

