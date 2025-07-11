# Root Makefile for Java Basic Projects
# This Makefile will run make commands in each project subdirectory

SUBDIRS := \
  Abstract-Classes \
  Array-Algorithms \
  ArrayList-Basic \
  Arrays-Basic \
  Classes-Objects \
  Collections-Utility \
  Constants-Finals \
  Constructors \
  CSV-File-Handling \
  Custom-Exceptions \
  Debugging-Techniques \
  Encapsulation \
  Error-Exception-Handling \
  File-Handling \
  For-Loops \
  HashMap-Basic \
  HashSet \
  HelloWorld \
  If-Else-Conditions \
  Inheritance-Basic \
  Input-Output \
  Instance-Variables \
  Interfaces-Basic \
  LinkedList \
  Loop-Control \
  Method-Overloading \
  Methods-Basic \
  Multidimensional-Arrays \
  Operators-Expressions \
  Polymorphism \
  Recursion \
  Regular-Expressions \
  Serialization-Basic \
  Static-Members \
  String-Algorithms \
  String-Manipulation \
  StringBuilder-StringBuffer \
  Switch-Statement \
  Text-File-Processing \
  Unit-Testing-Basic \
  Variable-Scope \
  Variables-DataTypes \
  While-Loops

.PHONY: all clean help

all:   ## Build all projects
	@for dir in $(SUBDIRS); do \
	  echo "\n===== Building project: $$dir ====="; \
	  $(MAKE) -C $$dir || exit 1; \
	done

clean: ## Clean all projects
	@for dir in $(SUBDIRS); do \
	  echo "\n===== Cleaning project: $$dir ====="; \
	  $(MAKE) -C $$dir clean || exit 1; \
	done

help:  ## Show this help message
	@echo "Java Basic Projects - Root Makefile"
	@echo "Available targets:"
	@echo "  make        - Build all projects (recursively)"
	@echo "  make clean  - Clean all projects (recursively)"
	@echo "  make help   - Show this help message" 