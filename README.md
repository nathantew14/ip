# Gertrude task tracker

This is a project template for a greenfield Java project. It's named to be reminiscent of a grandmotherly figure, _Gertrude_. Given below are instructions on how to use it.

## Setting up in VSCode
I can't actually get Gradle for VSCode to work, so...  
Prerequisites: JDK 17.

1. Run `./gradlew run` for the GUI project.
2. Alternatively, navigate to `src/main/java/gertrude/gertrude.java and click the Run button in VSCode to launch the CLI version`

**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.

## Usage
List of commands can be invoked with "help". It will also be shown whenever an unrecognised command is input.

## Things to note
Data is stored under `/data/gertrude.txt`, and is shared across the CLI and GUI app.

## Commands
`./text-ui-test/runtest.sh`  
`./gradlew checkstyleMain checkstyleTest`  
`./gradlew build`  
`./gradlew run`  
`java -jar ./build/libs/gertrude.jar`