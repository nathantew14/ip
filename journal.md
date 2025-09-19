# Gradle
## Build error
When starting up VSCode, some Gradle Build always fails:
```
[info] [gradle-server] Gradle Server started, listening on 56332
[info] Gradle client connected to server
[info] Java Home: /Users/nathan/.vscode/extensions/redhat.java-1.44.0-darwin-x64/jre/21.0.8-macosx-x86_64
[info] JVM Args: --add-opens=java.base/java.util=ALL-UNNAMED,--add-opens=java.base/java.lang=ALL-UNNAMED,--add-opens=java.base/java.lang.invoke=ALL-UNNAMED,--add-opens=java.prefs/java.util.prefs=ALL-UNNAMED,--add-opens=java.base/java.nio.charset=ALL-UNNAMED,--add-opens=java.base/java.net=ALL-UNNAMED,--add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED,-XX:MaxMetaspaceSize=256m,-XX:+HeapDumpOnOutOfMemoryError,-Xms256m,-Xmx512m,-Dfile.encoding=UTF-8,-Duser.country=GB,-Duser.language=en,-Duser.variant
[info] Gradle User Home: /Users/nathan/.gradle
[info] Gradle Version: 7.6.2
[error] FAILURE: Build failed with an exception.

* What went wrong:
Could not open cp_init generic class cache for initialization script '/var/folders/d6/z7vyqxr15kd2mdyzn2cxxy7c0000gn/T/e104a5b198032d86a79fd896826497955faf162b756cbab822c5e4a154259a05.gradle' (/Users/nathan/.gradle/caches/7.6.2/scripts/2uvdd1ff338gk1ror3ro7he0v).
> BUG! exception in phase 'semantic analysis' in source unit '_BuildScript_' Unsupported class file major version 65

* Try:
> Run with --stacktrace option to get the stack trace.
> Run with --info or --debug option to get more log output.
> Run with --scan to get full insights.

* Get more help at https://help.gradle.org

CONFIGURE FAILED in 9s
[error] [gradle-server] The supplied build action failed with an exception.
[error] Error getting build for /Users/nathan/development/cs2103t/ip: The supplied build action failed with an exception.
[info] Found 0 tasks
[info] Java Home: /Users/nathan/.vscode/extensions/redhat.java-1.44.0-darwin-x64/jre/21.0.8-macosx-x86_64
[info] JVM Args: --add-opens=java.base/java.util=ALL-UNNAMED,--add-opens=java.base/java.lang=ALL-UNNAMED,--add-opens=java.base/java.lang.invoke=ALL-UNNAMED,--add-opens=java.prefs/java.util.prefs=ALL-UNNAMED,--add-opens=java.base/java.nio.charset=ALL-UNNAMED,--add-opens=java.base/java.net=ALL-UNNAMED,--add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED,-XX:MaxMetaspaceSize=256m,-XX:+HeapDumpOnOutOfMemoryError,-Xms256m,-Xmx512m,-Dfile.encoding=UTF-8,-Duser.country=GB,-Duser.language=en,-Duser.variant
[info] Gradle User Home: /Users/nathan/.gradle
[info] Gradle Version: 7.6.2
[error] FAILURE: Build failed with an exception.

* What went wrong:
Could not open cp_init generic class cache for initialization script '/var/folders/d6/z7vyqxr15kd2mdyzn2cxxy7c0000gn/T/e104a5b198032d86a79fd896826497955faf162b756cbab822c5e4a154259a05.gradle' (/Users/nathan/.gradle/caches/7.6.2/scripts/2uvdd1ff338gk1ror3ro7he0v).
> BUG! exception in phase 'semantic analysis' in source unit '_BuildScript_' Unsupported class file major version 65

* Try:
> Run with --stacktrace option to get the stack trace.
> Run with --info or --debug option to get more log output.
> Run with --scan to get full insights.

* Get more help at https://help.gradle.org

CONFIGURE FAILED in 102ms
[error] [gradle-server] The supplied build action failed with an exception.
[error] Error getting build for /Users/nathan/development/cs2103t/ip: The supplied build action failed with an exception.
```

## Run error
my code uses the java scanner to read inputs from the command line and it works when i run it with java, but throws this error when i run it with gradle

```
Exception in thread "main" java.util.NoSuchElementException: No line found
        at java.base/java.util.Scanner.nextLine(Unknown Source)
        at gertrude.util.Ui.readCommand(Ui.java:26)
        at gertrude.Gertrude.run(Gertrude.java:62)
        at gertrude.Gertrude.main(Gertrude.java:33)

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':run'.
> Process 'command '/Users/nathan/.vscode/extensions/redhat.java-1.44.0-darwin-x64/jre/21.0.8-macosx-x86_64/bin/java'' finished with non-zero exit value 1
```

## error when trying to use JUnit
my build.gradle includes some testing dependencies:

dependencies {
    testImplementation group: 'org.junit.jupiter', name: 'junit-jupiter-api', version: '5.10.0'
    testRuntimeOnly group: 'org.junit.jupiter', name: 'junit-jupiter-engine', version: '5.10.0'
}

however, in my test file: ([root folder]/src/test/java/gertrude/util)
```
package gertrude.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeParserTest {

    @Test
    void parse_validDateTimeFormats_shouldReturnCorrectLocalDateTime() {
        assertEquals(LocalDateTime.of(2019, 12, 2, 18, 0), 
                     DateTimeParser.parse("2/12/2019 1800"));
        assertEquals(LocalDateTime.of(2019, 12, 2, 18, 0), 
                     DateTimeParser.parse("2/12/2019 18:00"));
        assertEquals(LocalDateTime.of(2019, 12, 2, 6, 0), 
                     DateTimeParser.parse("2/12/2019 6:00am"));
        assertEquals(LocalDateTime.of(2019, 12, 2, 6, 0), 
                     DateTimeParser.parse("2/12/2019 6am"));
        assertEquals(LocalDateTime.of(2019, 12, 2, 0, 0), 
                     DateTimeParser.parse("2/12/2019"));
        assertEquals(LocalDateTime.of(2019, 12, 2, 18, 0), 
                     DateTimeParser.parse("2019-12-02 1800"));
        assertEquals(LocalDateTime.of(2019, 12, 2, 18, 0), 
                     DateTimeParser.parse("2019-12-02 18:00"));
        assertEquals(LocalDateTime.of(2019, 12, 2, 6, 0), 
                     DateTimeParser.parse("2019-12-02 6:00am"));
        assertEquals(LocalDateTime.of(2019, 12, 2, 6, 0), 
                     DateTimeParser.parse("2019-12-02 6am"));
        assertEquals(LocalDateTime.of(2019, 12, 2, 0, 0), 
                     DateTimeParser.parse("2019-12-02"));
    }

    @Test
    void parse_timeOnlyFormats_shouldReturnCorrectLocalDateTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime parsedTime = DateTimeParser.parse("1800");
        assertEquals(now.toLocalDate().atTime(18, 0), parsedTime.minusDays(parsedTime.isBefore(now) ? 1 : 0));
        
        parsedTime = DateTimeParser.parse("6:00am");
        assertEquals(now.toLocalDate().atTime(6, 0), parsedTime.minusDays(parsedTime.isBefore(now) ? 1 : 0));
    }

    @Test
    void parse_invalidFormats_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> DateTimeParser.parse("invalid-date"));
        assertThrows(IllegalArgumentException.class, () -> DateTimeParser.parse("32/12/2019"));
        assertThrows(IllegalArgumentException.class, () -> DateTimeParser.parse("2019-13-02"));
        assertThrows(IllegalArgumentException.class, () -> DateTimeParser.parse("25:00"));
    }
}
```

VSCode is raising errors that "The declared package "gertrude.util" does not match the expected package "test.java.gertrude.util"Java(536871240)"

and also "The import org.junit cannot be resolvedJava(268435846)"

GPT recommended to run the `Java: Clean Java Language Server Workspace` command and it actually seemed to work, VSCode is no longer complaining.

# Hair-tearing issues faced
## Checkstyle for Java VSCode extension not working
`com.puppycrawl.tools.checkstyle.api.CheckstyleException: Property ${config_loc} has not been set`
Fixed: https://github.com/jdneo/vscode-checkstyle/issues/277
Set this in the project .vscode/settings file:
```
    "java.checkstyle.properties": {
        "config_loc": "${workspaceFolder}/config/checkstyle"
    }  

```
## Messages VBox won't grow
Confirmed with styling that the `ScrollPane` does resize, but everything inside refuses to.
Tried everything imagineable:
```
maxWidth="-Infinity"
VBox.vgrow="ALWAYS"
AnchorPane.topAnchor="0.0"
AnchorPane.leftAnchor="0.0"
AnchorPane.rightAnchor="0.0"
AnchorPane.bottomAnchor="41.0"
```
Only thing that changed the width was hard-setting a `minWidth` for `VBox` or `DialogBox`.

UPDATE: SETTING `maxWidth="-Infinity"`IN THE VBox WAS THE PROBLEM WTF AOISHDFOAIL;KSHDFN ;OAHSDFLNIHAS
why does the AnchorPane have that and why does it work...

## VSCode 
https://github.com/nus-cs2103-AY2526S1/forum/issues/202

What finally worked: followed the asker's solution in [this thread](https://stackoverflow.com/questions/68026530/visual-studio-code-not-recognizing-java-project):
- specified `java.jdt.ls.java.home` java path
- found the java path with `echo $HOME` as suggested [here](https://stackoverflow.com/a/73146672/7577786).

# Credit
## Images
https://cdn.vectorstock.com/i/1000v/09/25/smiling-cartoon-grandma-portrait-flat-design-vector-47760925.jpg
