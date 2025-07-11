# Input-Output - Makefile
# Course: ITEC313 - Object-Oriented Programming

JAVAC = javac
JAVA = java
MAIN_CLASS = InputOutput
ARGS = 

.PHONY: all compile run clean help

all: compile

compile:
	$(JAVAC) $(MAIN_CLASS).java
	$(JAVAC) examples/*.java

run: compile
	$(JAVA) $(MAIN_CLASS) $(ARGS)

run-example1: compile
	$(JAVA) -cp . examples.Example1

run-example2: compile
	$(JAVA) -cp . examples.Example2

run-advanced: compile
	$(JAVA) -cp . examples.Advanced

clean:
	rm -f *.class examples/*.class

help:
	@echo "Available targets:"
	@echo "  compile        - Compile the program and examples"
	@echo "  run            - Compile and run the main program"
	@echo "  run ARGS=...   - Run with command-line arguments"
	@echo "  run-example1   - Run Example1.java"
	@echo "  run-example2   - Run Example2.java"
	@echo "  run-advanced   - Run Advanced.java"
	@echo "  clean          - Remove compiled files"
	@echo "  help           - Show this help"
	@echo ""
	@echo "Examples:"
	@echo "  make run ARGS=\"hello world 42\""
	@echo "  make run-example1"
