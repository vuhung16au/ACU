#!/bin/bash

# Script to create run-jar.sh files for all JavaFX projects
# This script will generate run-jar.sh files for projects that don't have them

echo "🚀 Creating run-jar.sh scripts for all JavaFX projects..."
echo "========================================================"

# Function to create run-jar.sh for a project
create_run_jar_script() {
    local project_dir=$1
    local jar_name=$2
    local project_name=$3
    
    # Create the run-jar.sh script (force recreate)
    cat > "$project_dir/run-jar.sh" << EOF
#!/bin/bash

# $project_name - JAR Runner Script
# This script creates dependencies and runs the JAR with proper JavaFX module path

echo "🚀 $project_name - JAR Runner Script"
echo "====================================="

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Error: Maven is not installed or not in PATH"
    echo "Please install Maven first: https://maven.apache.org/install.html"
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Error: Java is not installed or not in PATH"
    echo "Please install Java 11 or higher"
    exit 1
fi

echo "✅ Maven and Java are available"

# Clean and package the project
echo ""
echo "🔨 Building the project and creating JAR..."
mvn clean package

if [ \$? -ne 0 ]; then
    echo "❌ Error: Build failed"
    exit 1
fi

echo "✅ Build successful"

# Copy dependencies to lib directory
echo ""
echo "📦 Copying JavaFX dependencies to lib directory..."
mvn dependency:copy-dependencies -DoutputDirectory=lib

if [ \$? -ne 0 ]; then
    echo "❌ Error: Failed to copy dependencies"
    exit 1
fi

echo "✅ Dependencies copied to lib directory"

# Find JavaFX version and path
JAVAFX_PATH=\$(find lib -name "javafx-controls-*.jar" | head -1 | xargs dirname)

if [ -z "\$JAVAFX_PATH" ]; then
    echo "❌ Error: JavaFX libraries not found in lib directory"
    exit 1
fi

echo "✅ JavaFX libraries found at: \$JAVAFX_PATH"

# Run the JavaFX application
echo ""
echo "🎯 Running the $project_name from JAR..."
echo "Note: A window should open with the JavaFX application"
echo ""

java --module-path "\$JAVAFX_PATH" \\
     --add-modules javafx.controls,javafx.fxml \\
     -jar target/$jar_name.jar

echo ""
echo "👋 Thanks for using the $project_name!"
EOF

    # Make the script executable
    chmod +x "$project_dir/run-jar.sh"
    echo "✅ Created $project_dir/run-jar.sh"
}

# Create run-jar.sh for each project
# Using a simpler approach with individual calls

create_run_jar_script "01-01-JavaFX-HelloWorld" "JavaFX-HelloWorld-1.0" "JavaFX HelloWorld Demo"
create_run_jar_script "01-02-JavaFX.Button" "javafx-button-demo-executable" "JavaFX Button Demo"
create_run_jar_script "01-03-Panes.UI.Controls.Shapes" "javafx-shapes-demo-1.0.0" "JavaFX Shapes Demo"
create_run_jar_script "01-04-NodeStyleRotateDemo" "node-style-rotate-demo-1.0.0" "JavaFX Node Style and Rotate Demo"
create_run_jar_script "01-05-MoreShapes" "more-shapes-demo-1.0.0" "JavaFX More Shapes Demo"
create_run_jar_script "01-06-TextFontColor" "text-font-color-demo-1.0.0" "JavaFX Text Font Color Demo"
create_run_jar_script "01-07-ImageView" "imagedemo-1.0.0" "JavaFX Image View Demo"
create_run_jar_script "01-08-LayoutPanes" "javafx-layout-panes-demo-1.0.0" "JavaFX Layout Panes Demo"
create_run_jar_script "01-09-ClockPane" "javafx-clockpanesdemo-1.0.0" "JavaFX Clock Pane Demo"
create_run_jar_script "02-01-HandleEvent" "javafx-handleevent-demo-1.0.0" "JavaFX Handle Event Demo"
create_run_jar_script "02-02-ControlCircleWithoutEventHandling" "javafx-controlcirclewithouteventhandling-demo-1.0.0" "JavaFX Control Circle Without Event Handling Demo"
create_run_jar_script "02-03-ControlCircle" "javafx-controlcircle-demo-1.0.0" "JavaFX Control Circle Demo"
create_run_jar_script "02-04-ShowInnerClass" "javafx-showinnerclass-demo-1.0.0" "JavaFX Show Inner Class Demo"
create_run_jar_script "02-05-AnonymousHandlerDemo" "javafx-anonymoushandlerdemo-1.0.0" "JavaFX Anonymous Handler Demo"
create_run_jar_script "02-06-LoanCalculator" "javafx-loancalculator-demo-1.0.0" "JavaFX Loan Calculator Demo"
create_run_jar_script "02-07-KeyMouseEventDemo" "javafx-keymouseeventdemo-1.0.0" "JavaFX Key Mouse Event Demo"
create_run_jar_script "02-08-ControlCircleWithMouseAndKey" "javafx-controlcirclewithmouseandkey-demo-1.0.0" "JavaFX Control Circle With Mouse And Key Demo"
create_run_jar_script "02-09-DisplayResizableClock" "javafx-displayresizableclock-demo-1.0.0" "JavaFX Display Resizable Clock Demo"
create_run_jar_script "02-10-PathTransitionDemo" "pathtransitiondemo-1.0.0" "JavaFX Path Transition Demo"
create_run_jar_script "02-11-FlagRisingAnimation" "javafx-flagrisinganimation-demo-1.0.0" "JavaFX Flag Rising Animation Demo"
create_run_jar_script "02-12-FadeTransitionDemo" "javafx-fadetransitiondemo-1.0.0" "JavaFX Fade Transition Demo"
create_run_jar_script "02-13-TimelineDemo" "javafx-timelinedemo-1.0.0" "JavaFX Timeline Demo"
create_run_jar_script "02-14-ClockAnimation" "javafx-clockanimation-demo-1.0.0" "JavaFX Clock Animation Demo"
create_run_jar_script "02-15-BouncingBall" "javafx-bouncingball-demo-1.0.0" "JavaFX Bouncing Ball Demo"
create_run_jar_script "03-01-JavaFx-ControlUI-Multimedia-TicTacToe" "javafx-tictactoe-demo-1.0.0" "JavaFX Tic Tac Toe Demo"
create_run_jar_script "04-01-Generics" "generics-demo-1.0.0" "JavaFX Generics Demo"
create_run_jar_script "05-01-FileClass" "javafx-fileclass-demo-1.0.0" "JavaFX File Class Demo"
create_run_jar_script "05-02-BinaryIO" "binary-io-demo-1.0.0" "JavaFX Binary IO Demo"
create_run_jar_script "06-01-Recursion" "recursion-demo-1.0.0" "JavaFX Recursion Demo"
create_run_jar_script "07-01-List-Stack-Queue-PriorityQueue" "javafx-list-stack-queue-priorityqueue-demo-1.0.0" "JavaFX List Stack Queue Priority Queue Demo"
create_run_jar_script "07-02-Sets-Maps" "sets-and-maps-demo-1.0.0" "JavaFX Sets and Maps Demo"
create_run_jar_script "07-03-MultipleBounceBall" "javafx-multiplebounceball-demo-1.0.0" "JavaFX Multiple Bounce Ball Demo"
create_run_jar_script "07-04-ArrayLinkedList" "javafx-arraylinkedlist-demo-1.0.0" "JavaFX Array Linked List Demo"
create_run_jar_script "07-05-GeometricObjectComparator" "javafx-geometricobjectcomparator-demo-1.0.0" "JavaFX Geometric Object Comparator Demo"
create_run_jar_script "07-06-SortString" "javafx-sortstring-demo-1.0.0" "JavaFX Sort String Demo"
create_run_jar_script "07-07-PriorityQueue" "javafx-priorityqueue-demo-1.0.0" "JavaFX Priority Queue Demo"
create_run_jar_script "07-08-EvaluateExpression" "javafx-evaluateexpression-demo-1.0.0" "JavaFX Evaluate Expression Demo"
create_run_jar_script "08-01-LinearSearch" "linear-search-demo-1.0.0" "JavaFX Linear Search Demo"
create_run_jar_script "08-02-BinarySearch" "binary-search-demo-1.0.0" "JavaFX Binary Search Demo"
create_run_jar_script "08-03-ClosestPair" "closest-pair-demo-1.0.0" "JavaFX Closest Pair Demo"
create_run_jar_script "08-04-SelectionSortNew" "selection-sort-new-demo-1.0.0" "JavaFX Selection Sort New Demo"
create_run_jar_script "08-05-PerformanceTest" "performance-test-demo-1.0.0" "JavaFX Performance Test Demo"
create_run_jar_script "08-06-Fibonacci" "fibonacci-demo-1.0.0" "JavaFX Fibonacci Demo"
create_run_jar_script "08-07-PrimeNumbers" "prime-numbers-demo-1.0.0" "JavaFX Prime Numbers Demo"
create_run_jar_script "08-08-EightQueens" "eight-queens-demo-1.0.0" "JavaFX Eight Queens Demo"
create_run_jar_script "09-01-Sorting" "sorting-demo-1.0.0" "JavaFX Sorting Demo"
create_run_jar_script "09-02-HeapSort" "heapsort-demo-1.0.0" "JavaFX Heap Sort Demo"
create_run_jar_script "10-01-ArrayList" "arraylist-demo-1.0.0" "JavaFX ArrayList Demo"
create_run_jar_script "10-02-LinkedList" "linkedlist-demo-1.0.0" "JavaFX LinkedList Demo"
create_run_jar_script "10-03-Stack" "javafx-stack-demo-1.0.0" "JavaFX Stack Demo"
create_run_jar_script "10-04-Queue" "javafx-queue-demo-1.0.0" "JavaFX Queue Demo"
create_run_jar_script "11-01-BinarySearchTrees" "binary-search-trees-demo-1.0.0" "JavaFX Binary Search Trees Demo"
create_run_jar_script "11-02-HuffmanCode" "huffman-code-demo-1.0.0" "JavaFX Huffman Code Demo"
create_run_jar_script "12-01-AVL-Trees" "avl-trees-demo-1.0.0" "JavaFX AVL Trees Demo"
create_run_jar_script "12-02-Probing" "probing-demo-1.0.0" "JavaFX Probing Demo"
create_run_jar_script "12-03-MapHash" "map-hash-demo-1.0.0" "JavaFX Map Hash Demo"

echo ""
echo "🎉 All run-jar.sh scripts have been created!"
echo "You can now run any project with: ./run-jar.sh" 