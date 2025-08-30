Using the VSCode integrated Copilot panel with GPT-4.1.

# Level-0
Used GPT-4.1 to change the ASCII art from Duke to Gertrude, but it can't seem to get the ASCII art right.Neither could Deepseek. Gave up and removed the banner. Also generated the intro in a grandmotherly tone.

# Level-1
Didn't remember how to read from the standard input, so AI helped with that, and helpfully added labelling of the messages. Auto-suggest in the CLI also helped add handling of the "bye" function without me telling it to. That's pretty impressive!

# Level-2
AI generation struggles with doing multiple things at once, so have to prompt it step-by-step.

# Level-3
AI generation automatically added error handling! Impressive.

# Level-4
Copying the entire question prompt results in an error. Had to try it haha.
AI was over-eager in writing an extremely detailed logic flow to detect invalid combinations of /by, /start and /end flags, had to remove some contradicting checks.

# A-TextUiTesting
AI was unable to tell me to use chmod to allow me to run my `runtest.sh`

# Level-5
Used AI to generate the exception class and use it throughout Gertrude.

# Level-6
Hit monthly chat messages quota on the integrated VSCode Copilot :(

# A-Enums
Used Deepseek to refactor my code to use Enums in place of the command prefixes and task tags (`/by`, `/start` and `/end`), as well as to use switch-case instead.
Used Deepseek again to flatten nested if-else blocks and it also helpfully abstracted some repeated code into methods that could be reused, such as validateTaskIndex.

The automated test ensures the refactors didn't break anything (that I remembered to test).

# Level-7
Prompt output from the question prompt looked mostly correct, but I made a general formatToFile that could be reused by the children of CompleteableTask. For some reason, saveTasksToFile was not used in the generated code.

Prompted again to use an enum to represent the outcome of loading the tasks from file. Unfortunately, will need more complicated testing procedure to account for the different welcome messages.

Will also need to handle the different starting states when testing: save file available, not available or corrupted.

Also asked AI (a few times) to give me a `help` message (I legit forgot the commands myself)

# Level-8
Tried GPT-5 and Claude Sonnet 4 but it does a bit more than I'd like it to, like shortening variable names unnecessarily, undoing some of my previous simple refactorings or just general complex logic that is not necessary.

GPT-4o did much better.

Unfortunately, the auto-generated datetime formats were a little unintuitive, and I took more time than I would have liked to fix it up in a few commits.

Introducing datetime also makes the testing even more complicated to do properly.

# A-MoreOOP
Refactoring step-by-step mostly worked well, although the Copilot GPT struggled with trying to import static class member enums. For some reason, normal GPT on the website recognised that it wouldn't really work, and I either had to separate the enums into their own files or start using packages.

# A-Packages
GPT super quickly refactored most things into packages, and I just had to mop up the leftovers.

# A-JUnit
GPT was able to generate tests for DateTimeParser mostly correctly, I just had to add some handling for the part that uses now(), which is a bad idea that I don't have time to fix right now.

The generated tests for the saved Tasks parser reminded me that Event and Deadline were not returning LocalDateTime objects, but rather strings. Refactor that and re-prompt to write the correct tests.

Weird that the first test run seemed to miss the "T" in the ISO format input... idk what happened there and I have no time to investigate.