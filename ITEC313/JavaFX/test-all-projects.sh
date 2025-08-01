#!/bin/bash

# Script to test all JavaFX projects
# This script will attempt to build and run each project to ensure they work correctly

echo "🧪 Testing all JavaFX projects..."
echo "=================================="

# Function to test a project
test_project() {
    local project_dir=$1
    local project_name=$2
    
    echo ""
    echo "🔍 Testing $project_name..."
    echo "================================"
    
    if [ ! -d "$project_dir" ]; then
        echo "❌ Directory $project_dir not found, skipping..."
        return 1
    fi
    
    cd "$project_dir"
    
    # Check if pom.xml exists
    if [ ! -f "pom.xml" ]; then
        echo "❌ pom.xml not found in $project_dir, skipping..."
        cd ..
        return 1
    fi
    
    # Check if run-jar.sh exists
    if [ ! -f "run-jar.sh" ]; then
        echo "❌ run-jar.sh not found in $project_dir, skipping..."
        cd ..
        return 1
    fi
    
    # Make run-jar.sh executable
    chmod +x run-jar.sh
    
    # Test build only (don't run the application to avoid opening too many windows)
    echo "🔨 Building $project_name..."
    mvn clean package -q
    
    if [ $? -eq 0 ]; then
        echo "✅ $project_name built successfully"
        
        # Check if JAR file was created
        if ls target/*.jar 1> /dev/null 2>&1; then
            echo "✅ JAR file created successfully"
        else
            echo "❌ JAR file not found"
            cd ..
            return 1
        fi
    else
        echo "❌ $project_name build failed"
        cd ..
        return 1
    fi
    
    cd ..
    return 0
}

# Test all projects
projects=(
    "01-01-JavaFX-HelloWorld:JavaFX HelloWorld"
    "01-02-JavaFX.Button:JavaFX Button Demo"
    "01-03-Panes.UI.Controls.Shapes:JavaFX Shapes Demo"
    "01-04-NodeStyleRotateDemo:JavaFX Node Style and Rotate Demo"
    "01-05-MoreShapes:JavaFX More Shapes Demo"
    "01-06-TextFontColor:JavaFX Text Font Color Demo"
    "01-07-ImageView:JavaFX Image View Demo"
    "01-08-LayoutPanes:JavaFX Layout Panes Demo"
    "01-09-ClockPane:JavaFX Clock Pane Demo"
    "02-01-HandleEvent:JavaFX Handle Event Demo"
    "02-02-ControlCircleWithoutEventHandling:JavaFX Control Circle Without Event Handling Demo"
    "02-03-ControlCircle:JavaFX Control Circle Demo"
    "02-04-ShowInnerClass:JavaFX Show Inner Class Demo"
    "02-05-AnonymousHandlerDemo:JavaFX Anonymous Handler Demo"
    "02-06-LoanCalculator:JavaFX Loan Calculator Demo"
    "02-07-KeyMouseEventDemo:JavaFX Key Mouse Event Demo"
    "02-08-ControlCircleWithMouseAndKey:JavaFX Control Circle With Mouse And Key Demo"
    "02-09-DisplayResizableClock:JavaFX Display Resizable Clock Demo"
    "02-10-PathTransitionDemo:JavaFX Path Transition Demo"
    "02-11-FlagRisingAnimation:JavaFX Flag Rising Animation Demo"
    "02-12-FadeTransitionDemo:JavaFX Fade Transition Demo"
    "02-13-TimelineDemo:JavaFX Timeline Demo"
    "02-14-ClockAnimation:JavaFX Clock Animation Demo"
    "02-15-BouncingBall:JavaFX Bouncing Ball Demo"
    "03-01-JavaFx-ControlUI-Multimedia-TicTacToe:JavaFX Tic Tac Toe Demo"
    "04-01-Generics:JavaFX Generics Demo"
    "05-01-FileClass:JavaFX File Class Demo"
    "05-02-BinaryIO:JavaFX Binary IO Demo"
    "06-01-Recursion:JavaFX Recursion Demo"
    "07-01-List-Stack-Queue-PriorityQueue:JavaFX List Stack Queue Priority Queue Demo"
    "07-02-Sets-Maps:JavaFX Sets and Maps Demo"
    "07-03-MultipleBounceBall:JavaFX Multiple Bounce Ball Demo"
    "07-04-ArrayLinkedList:JavaFX Array Linked List Demo"
    "07-05-GeometricObjectComparator:JavaFX Geometric Object Comparator Demo"
    "07-06-SortString:JavaFX Sort String Demo"
    "07-07-PriorityQueue:JavaFX Priority Queue Demo"
    "07-08-EvaluateExpression:JavaFX Evaluate Expression Demo"
    "08-01-LinearSearch:JavaFX Linear Search Demo"
    "08-02-BinarySearch:JavaFX Binary Search Demo"
    "08-03-ClosestPair:JavaFX Closest Pair Demo"
    "08-04-SelectionSortNew:JavaFX Selection Sort New Demo"
    "08-05-PerformanceTest:JavaFX Performance Test Demo"
    "08-06-Fibonacci:JavaFX Fibonacci Demo"
    "08-07-PrimeNumbers:JavaFX Prime Numbers Demo"
    "08-08-EightQueens:JavaFX Eight Queens Demo"
    "09-01-Sorting:JavaFX Sorting Demo"
    "09-02-HeapSort:JavaFX Heap Sort Demo"
    "10-01-ArrayList:JavaFX ArrayList Demo"
    "10-02-LinkedList:JavaFX LinkedList Demo"
    "10-03-Stack:JavaFX Stack Demo"
    "10-04-Queue:JavaFX Queue Demo"
    "11-01-BinarySearchTrees:JavaFX Binary Search Trees Demo"
    "11-02-HuffmanCode:JavaFX Huffman Code Demo"
    "12-01-AVL-Trees:JavaFX AVL Trees Demo"
    "12-02-Probing:JavaFX Probing Demo"
    "12-03-MapHash:JavaFX Map Hash Demo"
)

successful=0
failed=0

for project in "${projects[@]}"; do
    IFS=':' read -r project_dir project_name <<< "$project"
    
    if test_project "$project_dir" "$project_name"; then
        ((successful++))
    else
        ((failed++))
    fi
done

echo ""
echo "📊 Test Results Summary"
echo "======================"
echo "✅ Successful builds: $successful"
echo "❌ Failed builds: $failed"
echo "📈 Total projects tested: $((successful + failed))"

if [ $failed -eq 0 ]; then
    echo ""
    echo "🎉 All projects built successfully!"
    echo "You can now run any project with: cd <project-dir> && ./run-jar.sh"
else
    echo ""
    echo "⚠️  Some projects failed to build. Please check the errors above."
fi 