# Methods-Basic - Makefile
# Course: ITEC313 - Object-Oriented Programming

JAVAC = javac
JAVA = java
MAIN_CLASS = MethodsBasic

.PHONY: all compile run clean help

all: compile

compile:
	$(JAVAC) $(MAIN_CLASS).java

run: compile
	$(JAVA) $(MAIN_CLASS)

clean:
	rm -f *.class examples/*.class

help:
	@echo "Available targets:"
	@echo "  compile - Compile the program"
	@echo "  run     - Compile and run the program"
	@echo "  clean   - Remove compiled files"
	@echo "  help    - Show this help"
