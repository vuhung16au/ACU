# Java compiler and runtime
JAVAC=javac
JAVA=java
SRC_DIR=src/main/java/com/acu/javafx/sorting
BIN_DIR=target/classes
JAVA_FILES=$(SRC_DIR)/BubbleSort.java \
           $(SRC_DIR)/Heap.java \
           $(SRC_DIR)/HeapSort.java \
           $(SRC_DIR)/InsertionSort.java \
           $(SRC_DIR)/MergeSort.java \
           $(SRC_DIR)/QuickSort.java \
           $(SRC_DIR)/RadixSort.java \
           $(SRC_DIR)/SortingDemo.java

CLASSES=BubbleSort Heap HeapSort InsertionSort MergeSort QuickSort RadixSort SortingDemo

# JavaFX setup (for SortingDemo)
JAVAFX_MODULES=javafx.controls,javafx.fxml,javafx.graphics,javafx.base
JAVAFX_PATH=$(shell mvn dependency:build-classpath -Dmdep.outputFile=cp.txt > /dev/null 2>&1 && cat cp.txt)

.PHONY: all clean build run_% run_SortingDemo

all: build

build:
	@mkdir -p $(BIN_DIR)
	$(JAVAC) -d $(BIN_DIR) -cp $(JAVAFX_PATH) $(JAVA_FILES)

clean:
	rm -rf $(BIN_DIR) cp.txt

# Run targets for each program
run_BubbleSort: build
	$(JAVA) -cp $(BIN_DIR) com.acu.javafx.sorting.BubbleSort

run_HeapSort: build
	$(JAVA) -cp $(BIN_DIR) com.acu.javafx.sorting.HeapSort

run_InsertionSort: build
	$(JAVA) -cp $(BIN_DIR) com.acu.javafx.sorting.InsertionSort

run_MergeSort: build
	$(JAVA) -cp $(BIN_DIR) com.acu.javafx.sorting.MergeSort

run_QuickSort: build
	$(JAVA) -cp $(BIN_DIR) com.acu.javafx.sorting.QuickSort

run_RadixSort: build
	$(JAVA) -cp $(BIN_DIR) com.acu.javafx.sorting.RadixSort

# JavaFX SortingDemo (requires JavaFX modules)
run_SortingDemo: build
	$(JAVA) --module-path $(JAVAFX_PATH) --add-modules $(JAVAFX_MODULES) -cp $(BIN_DIR) com.acu.javafx.sorting.SortingDemo

run-all-cli: build
	$(MAKE) run_BubbleSort
	$(MAKE) run_HeapSort
	$(MAKE) run_InsertionSort
	$(MAKE) run_MergeSort
	$(MAKE) run_QuickSort
	$(MAKE) run_RadixSort

