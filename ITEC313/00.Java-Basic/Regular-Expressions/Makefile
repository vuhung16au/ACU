# Makefile for Regular-Expressions project
JAVAC=javac
JAVA=java
SRC=RegularExpressions.java
EXAMPLES=examples/Example1.java examples/Example2.java examples/Advanced.java
CLASSES=RegularExpressions.class examples/Example1.class examples/Example2.class examples/Advanced.class

all: $(CLASSES)

%.class: %.java
	$(JAVAC) $<

run: RegularExpressions.class
	$(JAVA) RegularExpressions

run-example1: examples/Example1.class
	$(JAVA) -cp examples Example1

run-example2: examples/Example2.class
	$(JAVA) -cp examples Example2

run-advanced: examples/Advanced.class
	$(JAVA) -cp examples Advanced

clean:
	rm -f *.class examples/*.class
