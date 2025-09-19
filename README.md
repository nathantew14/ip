# Gertrude Task Tracker

Gertrude is a desktop app for managing tasks, optimized for use via a Command Line Interface (CLI). If you can type fast, Gertrude can help you manage your tasks efficiently. It's named to be reminiscent of a grandmotherly figure, _Gertrude_. Given below are instructions on how to use it.

## Quick Start

1. Ensure you have Java 17 or above installed on your computer.
2. Download the latest `.jar` file from the releases section.
3. Copy the file to the folder you want to use as the home folder for Gertrude.
4. Open a command terminal, navigate to the folder containing the `.jar` file, and run the following command:
   ```
   java -jar gertrude.jar
   ```
5. A CLI interface will appear, and you can start managing your tasks.

## Features

### Viewing Help: `help`
Shows a message explaining how to access the help page.

**Format:**
```
help
```

### Adding a Task: `add`
Adds a task to your task list. Tasks can be simple todos, deadlines, or events.

**Formats:**
- Add a todo:
  ```
  add:<description>
  ```
  Example: `add:Buy groceries`

- Add a deadline:
  ```
  add:<description> /by <deadline>
  ```
  Example: `add:Submit report /by 2023-12-01`

- Add an event:
  ```
  add:<description> /start <start time> /end <end time>
  ```
  Example: `add:Team meeting /start 2023-12-01 10:00 /end 2023-12-01 11:00`

### Listing All Tasks: `list`
Shows a list of all tasks in your task list.

**Format:**
```
list
```

### Marking a Task as Completed: `mark`
Marks a task as completed.

**Format:**
```
mark:<task id>
```
Example: `mark:2`

### Unmarking a Task: `unmark`
Marks a task as not completed.

**Format:**
```
unmark:<task id>
```
Example: `unmark:2`

### Removing a Task: `remove`
Removes a task from your task list.

**Format:**
```
remove:<task id>
```
Example: `remove:3`

### Finding Tasks: `find`
Finds tasks containing a specific keyword.

**Format:**
```
find:<keyword>
```
Example: `find:groceries`

### Exiting the Program: `bye`
Exits the program.

**Format:**
```
bye
```

## Saving the Data
Gertrude automatically saves your tasks to a file (`data/gertrude.txt`) after every command that modifies the task list. There is no need to save manually.

## Editing the Data File
The data file is stored in plain text format and can be edited manually. However, ensure the format remains valid to avoid errors when loading the data.

## FAQ

**Q: How do I transfer my data to another computer?**
A: Copy the `data/gertrude.txt` file to the same location on the new computer where the Gertrude `.jar` file is located.

## Command Summary

| Action       | Format, Examples                                      |
|--------------|-------------------------------------------------------|
| Add          | `add:<description>`                                  |
|              | `add:<description> /by <deadline>`                   |
|              | `add:<description> /start <start time> /end <end time>` |
| List         | `list`                                               |
| Mark         | `mark:<task id>`                                     |
| Unmark       | `unmark:<task id>`                                   |
| Remove       | `remove:<task id>`                                   |
| Find         | `find:<keyword>`                                     |
| Help         | `help`                                               |
| Exit         | `bye`                                                |

## Setting up in VSCode
I can't actually get Gradle for VSCode to work, so...  
Prerequisites: JDK 17.

Specific versions for MacOS:
```
>> ip % java --version 
openjdk 17.0.14 2025-01-21 LTS
OpenJDK Runtime Environment Zulu17.56+15-CA (build 17.0.14+7-LTS)
OpenJDK 64-Bit Server VM Zulu17.56+15-CA (build 17.0.14+7-LTS, mixed mode, sharing)
```

1. Run `./gradlew run` for the GUI project.
2. Alternatively, navigate to `src/main/java/gertrude/gertrude.java and click the Run button in VSCode to launch the CLI version`
3. To get VSCode to recognise the Java project, you might have to explicitly set the path to java in [.vscode/settings.json](.vscode/settings.json).

**Warning:** Keep the `src/main/java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.

## Useful CLI commands

| Action                        | Command                                      |
|-------------------------------|----------------------------------------------|
| Run a basic test              | `./text-ui-test/runtest.sh`                 |
| Run check for style           | `./gradlew checkstyleMain checkstyleTest`   |
| Build the project             | `./gradlew build`                           |
| Run the project               | `./gradlew run`                             |
| Generate a `.jar` file        | `./gradlew shadowJar`                       |
| Run a `.jar` file             | `java -jar ./build/libs/gertrude.jar`       |