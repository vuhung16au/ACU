# Interfaces-Basic - Makefile
# Course: ITEC313 - Object-Oriented Programming

JAVAC = javac
JAVA = java
MAIN_CLASS = InterfacesBasic

.PHONY: all compile run clean help examples

all: compile

compile:
	$(JAVAC) $(MAIN_CLASS).java
	$(JAVAC) examples/*.java

run: compile
	$(JAVA) $(MAIN_CLASS)

# Run individual examples
example1: compile
	$(JAVA) -cp examples Example1

example2: compile
	$(JAVA) -cp examples Example2

advanced: compile
	$(JAVA) -cp examples Advanced

# Run all examples
examples: example1 example2 advanced

clean:
	rm -f *.class examples/*.class tests/*.class

help:
	@echo "Available targets:"
	@echo "  compile  - Compile the main program and examples"
	@echo "  run      - Compile and run the main program"
	@echo "  example1 - Run Example1 (payment system)"
	@echo "  example2 - Run Example2 (default methods and lambdas)"
	@echo "  advanced - Run Advanced (nested interfaces and inheritance)"
	@echo "  examples - Run all examples"
	@echo "  clean    - Remove compiled files"
	@echo "  help     - Show this help"
