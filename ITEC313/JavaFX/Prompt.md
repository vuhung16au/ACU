# Implement Shortest path from one city to another city using Graph Theory

The purpose of this project is to implement the shortest path from one city to another city using Graph Theory.

Save your response to `13-05-ShortestPath-Australia-Graph/`

The logic of the class should be based on the .java files under folder
`13-05-ShortestPath-Australia-Graph/src/*.java`

Use the following cities in Australia as the nodes of the graph:
- Sydney
- Melbourne
- Brisbane
- Perth
- Adelaide
- Darwin
- Canberra
- Gold Coast
- Redlands
- Logan
- Newcastle
- Alice Springs
- Cairns
- Dampier

Help me decide the distance between each city.

Save the graph to a file called `australia-graph.json`.

The directory structure should be like this:
- `13-05-ShortestPath-Australia-Graph`
    - src
    - test
    - docs/: Please update `docs/algorithm.md` to include the algorithm to solve the Sudoku problem
    - docs/architecture.md: Please update `docs/architecture.md` to include the architecture of the app
    - docs/concepts.md: Please update `docs/concepts.md` to include the concepts of the app
    - docs/australia-graph.md: Explain the graph of the app, including the nodes and edges.
    - pom.xml
    - README.md: Include: Explain the logic of the app (short and sharp)
    - images/

Requirements for the UI: 
- Add a "Help" button to display the help message - how to use the app
- Add a "Demo" button to display the demo of the app, showing the shortest path from Sydney to Adelaide.
- Add a "About" button to display the about message
- Add a "Algorithm" button to display the algorithm to solve the shortest path problem. Also briefly mention other algorithms that can be used to solve the shortest path problem.
- Add a "Strategy" button to display the strategy to solve the shortest path problem
- Add a "Reset" button to reset the app to the initial state: Show the cities on the map, and the distance between each city.

Additional requirements:
- Show the cities on the map
- Use weighted edges to represent the distance between each city.
- Use the Dijkstra's algorithm to find the shortest path from one city to another city.

Make sure the following commands work:
- mvn test (runs on CLI, only apply for the backend of the app, not the GUI)
- mvn clean compile
- mvn javafx:run
without any errors.

Other requirements:
- Use ACU color schema
- Keep the code as simple as possible.
- Comment the code to explain the logic, for educational purposes.
- Use the same structure as the other projects in the repository (e.g `05-02-BinaryIO`)
- Use the same naming conventions as the other projects in the repository.
- Use the same documentation structure as the other projects in the repository.
- Use the same testing structure as the other projects in the repository.
- update <root-folder>/pom.xml to include the new project as a module 
- make sure `mvn clean compile` works without any errors under the root folder.

ACU color schema 
```
Purple: rgb(60, 16, 83), Hex: #3C1053 - sRGB(0.2353, 0.0627, 0.3255)
Red: rgb(242, 18, 12), Hex: #F2120C - sRGB(0.9490, 0.0706, 0.0471)
Black: rgb(0, 0, 0), Hex #FFFFFF — sRGB(1.0, 1.0, 1.0)
White: rgb(255, 255, 255),  Hex #000000 — sRGB(0.0, 0.0, 0.0)

Purple (Faculty of Law and Business):  rgb(181, 24, 37) -  #B51825

Warm Stone: Hex: #918B83 - RGB: rgb(145, 139, 131)

Name: Deep Charcoal
Hex: #302C2A
RGB: rgb(48, 44, 42)

Name: Soft Ivory
Hex: #F2EFEB
RGB: rgb(242, 239, 235)
```


---

# Modify `13-04-Sudoku-Graph` to use Graph Theory to solve the Sudoku problem

Save your response to `13-04-Sudoku-Graph/`

The logic of the class should be based on the .java files under folder
`13-04-Sudoku-Graph/src/*.java`


The directory structure should be like this:
- `13-04-Sudoku-Graph`
    - src
    - test
    - docs/: Please update `docs/algorithm.md` to include the algorithm to solve the Sudoku problem
    - docs/architecture.md: Please update `docs/architecture.md` to include the architecture of the app
    - docs/concepts.md: Please update `docs/concepts.md` to include the concepts of the app
    - pom.xml
    - README.md: Include: Explain the logic of the app (short and sharp)
    - images/

Modify the UI: 
- Add a "Help" button to display the help message - how to use the app
- Add a "About" button to display the about message
- Add a "Algorithm" button to display the algorithm to solve the Sudoku problem
- Add a "Strategy" button to display the strategy to solve the Sudoku problem
- Add a "Check" button to check if the Sudoku problem is solved
- Use different colors for numbers 1-9 and empty cells

Make sure the following commands work:
- mvn test (runs on CLI, only apply for the backend of the app, not the GUI)
- mvn clean compile
- mvn javafx:run
without any errors.


Other requirements:
- Use ACU color schema
- Keep the code as simple as possible.
- Comment the code to explain the logic, for educational purposes.
- Use the same structure as the other projects in the repository (e.g `05-02-BinaryIO`)
- Use the same naming conventions as the other projects in the repository.
- Use the same documentation structure as the other projects in the repository.
- Use the same testing structure as the other projects in the repository.
- update <root-folder>/pom.xml to include the new project as a module 
- make sure `mvn clean compile` works without any errors under the root folder.

ACU color schema 
```
Purple: rgb(60, 16, 83), Hex: #3C1053 - sRGB(0.2353, 0.0627, 0.3255)
Red: rgb(242, 18, 12), Hex: #F2120C - sRGB(0.9490, 0.0706, 0.0471)
Black: rgb(0, 0, 0), Hex #FFFFFF — sRGB(1.0, 1.0, 1.0)
White: rgb(255, 255, 255),  Hex #000000 — sRGB(0.0, 0.0, 0.0)

Purple (Faculty of Law and Business):  rgb(181, 24, 37) -  #B51825

Warm Stone: Hex: #918B83 - RGB: rgb(145, 139, 131)

Name: Deep Charcoal
Hex: #302C2A
RGB: rgb(48, 44, 42)

Name: Soft Ivory
Hex: #F2EFEB
RGB: rgb(242, 239, 235)
```


---

# Implement `ConnectedCircles` Problem with JavaFX

High level requirements:

```
The connected circles problem is to determine whether all circles in a two-
dimensional plane are connected. This problem can be solved using a depth-first
traversal.

The DFS algorithm has many applications. This section applies the DFS algorithm to solve
the connected circles problem.
In the connected circles problem, you determine whether all the circles in a two-dimensional
plane are connected. If all the circles are connected, they are painted as filled circles, as shown
in Figure 28.14a. Otherwise, they are not filled, as shown in Figure 28.14b.

We will write a program that lets the user create a circle by clicking a mouse in a blank area
that is not currently covered by a circle. As the circles are added, the circles are repainted filled
if they are connected or unfilled otherwise.
We will create a graph to model the problem. Each circle is a vertex in the graph. Two
circles are connected if they overlap. We apply the DFS in the graph, and if all vertices are
found in the depth-first search, the graph is connected.
```

See the attached image for the UI of the game as a reference.

The UI of the game should look like:
- Window size: 800x600 pixels (approximately as the canvas size)
- Users can click the mouse to create a circle. The circle is created at the mouse cursor position with fixed radius 20 (default, can be changed to 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 on the UI)
- Users can drag the mouse to move the circle.
- Users can change the color of the circle. The list of colors is: Red, Green, Blue, Yellow, Purple. Default color is Purple.
- "Same Color Check" checkbox to check if the circles with the same color are connected. Default is checked.
- "Reset" button to reset all the circles to the blank state (no circles on the canvas)
- "Randomize" button to randomly create 10 circles on the canvas with random positions and radii. After 10 circles are created, users can manually create more circles by clicking the mouse.
- "Check" button to check if all the circles are connected. If they are connected, the circles are painted as filled circles, as shown in Figure 28.14a. Otherwise, they are not filled, as shown in Figure 28.14b.
- "Algorithm" button to display the algorithm to solve the connected circles problem.
- "Help" button to display the help message.
- "About" button to display the about message.


Save your response to `13-03-ConnectedCircles-Problem/`

The logic of the class should be based on the .java files under folder
`13-03-ConnectedCircles-Problem/src/*.java`


The directory structure should be like this:
- `13-03-ConnectedCircles-Problem`
    - src
    - test
    - docs/
    - pom.xml
    - README.md: Include: Explain the logic of the app (short and sharp)
    - images/

Make sure the following commands work:
- mvn test
- mvn clean compile
- mvn javafx:run
without any errors.


Other requirements:
- Use ACU color schema
- Keep the code as simple as possible.
- Comment the code to explain the logic, for educational purposes.
- Use the same structure as the other projects in the repository (e.g `05-02-BinaryIO`)
- Use the same naming conventions as the other projects in the repository.
- Use the same documentation structure as the other projects in the repository.
- Use the same testing structure as the other projects in the repository.
- update <root-folder>/pom.xml to include the new project as a module 
- make sure `mvn clean compile` works without any errors under the root folder.

ACU color schema 
```
Purple: rgb(60, 16, 83), Hex: #3C1053 - sRGB(0.2353, 0.0627, 0.3255)
Red: rgb(242, 18, 12), Hex: #F2120C - sRGB(0.9490, 0.0706, 0.0471)
Black: rgb(0, 0, 0), Hex #FFFFFF — sRGB(1.0, 1.0, 1.0)
White: rgb(255, 255, 255),  Hex #000000 — sRGB(0.0, 0.0, 0.0)

Purple (Faculty of Law and Business):  rgb(181, 24, 37) -  #B51825

Warm Stone: Hex: #918B83 - RGB: rgb(145, 139, 131)

Name: Deep Charcoal
Hex: #302C2A
RGB: rgb(48, 44, 42)

Name: Soft Ivory
Hex: #F2EFEB
RGB: rgb(242, 239, 235)
```


---

---

# Add an "Algorithm" button, when click it, display the algorithm to solve the Nine Tails Game (BFS)

# Add a "Strategy" button, when click it, display the strategy to solve the Nine Tails Game

# Add a "Help" button, when click it, display how to play the Nine Tails Game


# Update Nine Tails Game

- Show "H" on the coins that are face up.
- Show "T" on the coins that are face down.
- When the user clicks on a coin, flip it and its neighbors.

---

# Implement Nine Tails Problem with JavaFX

High level requirements and description:
(attached image)

```
The nine tails problem can be reduced to the shortest path problem.

The nine tails problem is as follows. Nine coins are placed in a 3 * 3 matrix, with some
face up and some face down. A legal move is to take any coin that is face up and reverse it,
together with the coins adjacent to it (this does not include coins that are diagonally adjacent).

Your task is to find the minimum number of moves that lead to all coins being face down. For
example, start with the nine coins as shown in Figure 28.17a. After you flip the second coin in
the last row, the nine coins are now as shown in Figure 28.17b. After you flip the second coin
in the first row, the nine coins are all face down,


We will write a program that prompts the user to enter an initial state of the nine coins and
displays the solution, as shown in the following sample run:
```
Enter the initial nine coins Hs and Ts: HHHTTTHHH
The steps to flip the coins are
HHH
TTT
HHH
HHH
THT
TTT
TTT
TTT
TTT
```

Figure 28.17 correspond to three nodes in the graph. For convenience, we use a 3 * 3 matrix
to represent all nodes and use 0 for heads and 1 for tails. Since there are nine cells and each
cell is either 0 or 1, there are a total of 29 (512) nodes, labeled 0, 1, . . . , and 511, as shown
in Figure 28.18.

We assign an edge from node v to u if there is a legal move from u to v. Figure 28.19 shows
a partial graph. Note there is an edge from 511 to 47, since you can flip a cell in node 47 to
become node 511.
The last node in Figure 28.18 represents the state of nine face-down coins. For convenience,
we call this last node the target node. Thus, the target node is labeled 511. Suppose the initial
state of the nine tails problem corresponds to the node s. The problem is reduced to finding
the shortest path from node s to the target node, which is equivalent to finding the path from
node s to the target node in a BFS tree rooted at the target node.
```

Save your response to `13-01-NineTailsProblem/`

The logic of the class should be based on the .java files under folder
`13-01-NineTailsProblem/src/*.java`


The directory structure should be like this:
- `13-01-NineTailsProblem`
    - src
    - test
    - docs/
    - pom.xml
    - README.md: Include: Explain the logic of the app (short and sharp)
    - images/

additional requirements:
- Let users play the game by clicking the coins to flip them.
- Have a button to "solve" to show the solution steps.
- Size of the board is 3x3 (default) and the initial state of the coins is random.
- Have a button to "Reset" to reset the game to the initial state.
- Have a button to "Size" to change the size of the board.

Other requirements:
- Use ACU color schema
- Keep the code as simple as possible.
- Comment the code to explain the logic, for educational purposes.
- Use the same structure as the other projects in the repository (e.g `05-02-BinaryIO`)
- Use the same naming conventions as the other projects in the repository.
- Use the same documentation structure as the other projects in the repository.
- Use the same testing structure as the other projects in the repository.
- update <root-folder>/pom.xml to include the new project as a module 
- make sure `mvn clean compile` works without any errors under the root folder.

ACU color schema 
```
Purple: rgb(60, 16, 83), Hex: #3C1053 - sRGB(0.2353, 0.0627, 0.3255)
Red: rgb(242, 18, 12), Hex: #F2120C - sRGB(0.9490, 0.0706, 0.0471)
Black: rgb(0, 0, 0), Hex #FFFFFF — sRGB(1.0, 1.0, 1.0)
White: rgb(255, 255, 255),  Hex #000000 — sRGB(0.0, 0.0, 0.0)

Purple (Faculty of Law and Business):  rgb(181, 24, 37) -  #B51825

Warm Stone: Hex: #918B83 - RGB: rgb(145, 139, 131)

Name: Deep Charcoal
Hex: #302C2A
RGB: rgb(48, 44, 42)

Name: Soft Ivory
Hex: #F2EFEB
RGB: rgb(242, 239, 235)
```


---- 

# Implement `Point2D` class with JavaFX

High level requirements:

## (The Point class) Design a class named Point that meets the following
requirements:

- Two data fields `x` and `y` for representing a point with getter methods
- A no-arg constructor that constructs a point for `(0, 0)`
- A constructor that constructs a point with the specified `x` and `y` values
- Override the `equals` method. Point `p1` is said to be equal to point `p2` if
`p1.x == p2.x` and `p1.y == p2.y`.
- Override the `hashCode` method. (For reference, see the implementation of the
`Point2D` class in the Java API.)

Save your response to `12-04-Point2D-Class/`

The logic of the class should be based on the .java files under folder
`12-04-Point2D-Class/src/*.java`


The directory structure should be like this:
- `12-04-Point2D-Class`
    - src
    - test
    - docs/
    - pom.xml
    - README.md: Include: Explain the logic of the app (short and sharp)
    - images/

Other requirements:
- Use ACU color schema
- Keep the code as simple as possible.
- Comment the code to explain the logic, for educational purposes.
- Use the same structure as the other projects in the repository (e.g `05-02-BinaryIO`)
- Use the same naming conventions as the other projects in the repository.
- Use the same documentation structure as the other projects in the repository.
- Use the same testing structure as the other projects in the repository.
- update <root-folder>/pom.xml to include the new project as a module 
- make sure `mvn clean compile` works without any errors under the root folder.

ACU color schema 
```
Purple: rgb(60, 16, 83), Hex: #3C1053 - sRGB(0.2353, 0.0627, 0.3255)
Red: rgb(242, 18, 12), Hex: #F2120C - sRGB(0.9490, 0.0706, 0.0471)
Black: rgb(0, 0, 0), Hex #FFFFFF — sRGB(1.0, 1.0, 1.0)
White: rgb(255, 255, 255),  Hex #000000 — sRGB(0.0, 0.0, 0.0)

Purple (Faculty of Law and Business):  rgb(181, 24, 37) -  #B51825

Warm Stone: Hex: #918B83 - RGB: rgb(145, 139, 131)

Name: Deep Charcoal
Hex: #302C2A
RGB: rgb(48, 44, 42)

Name: Soft Ivory
Hex: #F2EFEB
RGB: rgb(242, 239, 235)
```


---

---

# Implement Snake game with JavaFX


Save your response to `60-03-Snake-Game/`

The UI of the gama should look like: 
- `60-03-Snake-Game/Snake-Game-master/images/UI.png`
- `60-03-Snake-Game/Snake-Game-master/images/UI2.PNG`
- However, try to replace PNG files with SVG files to make the game look more modern and professional.

The logic of the game should be based on the .java files under folder
`60-03-Snake-Game/Snake-Game-master/src/*.java`


Make sure the following commands work:
- mvn test
- mvn clean compile
- mvn javafx:run
without any errors.

The directory structure should be like this:
- `60-03-Snake-Game`
    - src
    - test
    - docs/
    - pom.xml
    - README.md: Include: Explain the logic of the app (short and sharp)
    - images/

Other requirements:
- Use ACU color schema
- Keep the code as simple as possible.
- Comment the code to explain the logic, for educational purposes.
- Use the same structure as the other projects in the repository (e.g `05-02-BinaryIO`)
- Use the same naming conventions as the other projects in the repository.
- Use the same documentation structure as the other projects in the repository.
- Use the same testing structure as the other projects in the repository.
- update <root-folder>/pom.xml to include the new project as a module 
- make sure `mvn clean compile` works without any errors under the root folder.

ACU color schema 
```
Purple: rgb(60, 16, 83), Hex: #3C1053 - sRGB(0.2353, 0.0627, 0.3255)
Red: rgb(242, 18, 12), Hex: #F2120C - sRGB(0.9490, 0.0706, 0.0471)
Black: rgb(0, 0, 0), Hex #FFFFFF — sRGB(1.0, 1.0, 1.0)
White: rgb(255, 255, 255),  Hex #000000 — sRGB(0.0, 0.0, 0.0)

Purple (Faculty of Law and Business):  rgb(181, 24, 37) -  #B51825

Warm Stone: Hex: #918B83 - RGB: rgb(145, 139, 131)

Name: Deep Charcoal
Hex: #302C2A
RGB: rgb(48, 44, 42)

Name: Soft Ivory
Hex: #F2EFEB
RGB: rgb(242, 239, 235)
```


---

# Implement 2048 game with JavaFX


Save your response to `60-02-2048-game/`

The UI of the gama should look like: 
`60-02-2048-game/2048-JAVA-master/screen.png`

The logic of the game should be based on the .java files under folder
`60-02-2048-game/2048-JAVA-master/src/game/*.java`


Make sure the following commands work:
- mvn test
- mvn clean compile
- mvn javafx:run
without any errors.

The directory structure should be like this:
- `60-02-2048-game`
    - src
    - test
    - docs/
    - pom.xml
    - README.md: Include: Explain the logic of the app (short and sharp)
    - images/

Other requirements:
- Use ACU color schema
- Keep the code as simple as possible.
- Comment the code to explain the logic, for educational purposes.
- Use the same structure as the other projects in the repository (e.g `05-02-BinaryIO`)
- Use the same naming conventions as the other projects in the repository.
- Use the same documentation structure as the other projects in the repository.
- Use the same testing structure as the other projects in the repository.
- update <root-folder>/pom.xml to include the new project as a module 
- make sure `mvn clean compile` works without any errors under the root folder.

ACU color schema 
```
Purple: rgb(60, 16, 83), Hex: #3C1053 - sRGB(0.2353, 0.0627, 0.3255)
Red: rgb(242, 18, 12), Hex: #F2120C - sRGB(0.9490, 0.0706, 0.0471)
Black: rgb(0, 0, 0), Hex #FFFFFF — sRGB(1.0, 1.0, 1.0)
White: rgb(255, 255, 255),  Hex #000000 — sRGB(0.0, 0.0, 0.0)

Purple (Faculty of Law and Business):  rgb(181, 24, 37) -  #B51825

Warm Stone: Hex: #918B83 - RGB: rgb(145, 139, 131)

Name: Deep Charcoal
Hex: #302C2A
RGB: rgb(48, 44, 42)

Name: Soft Ivory
Hex: #F2EFEB
RGB: rgb(242, 239, 235)
```

----

# Visualize the Hanoi Tower Solver (non-recursive: stack)

Save your response to `10-06-HanoiTowerSolver/`

Refer to the attachment for the logic of the hanoi tower solver algorithm.



# Implement Directory Size (non-recursive: queue)

Use a queue instead of using recursion.

Save your response to `10-07-DirectorySize/`


Source code files include:
- `10-07-DirectorySize/DirectorySize.java`
- `10-07-DirectorySize/DirectorySizeApp.java`
- `10-07-DirectorySize/Launcher.java`

Make sure the following commands work:
- mvn test
- mvn clean compile
- mvn javafx:run
without any errors.

The directory structure should be like this:
- `10-07-DirectorySize`
    - src
    - test
    - docs/
    - pom.xml
    - README.md: Include: Explain the logic of the app (short and sharp)
    - images/

Other requirements:
- Keep the code as simple as possible.
- Comment the code to explain the logic, for educational purposes.
- Use the same structure as the other projects in the repository (e.g `05-02-BinaryIO`)
- Use the same naming conventions as the other projects in the repository.
- Use the same documentation structure as the other projects in the repository.
- Use the same testing structure as the other projects in the repository.
- update <root-folder>/pom.xml to include the new project as a module



---

# Implement Hanoi Tower Solver (non-recursive: stack)

Use a stack instead of using recursion.

Save your response to `10-06-HanoiTowerSolver/`


Source code files include:
- `10-06-HanoiTowerSolver/HanoiTowerSolver.java`
- `10-06-HanoiTowerSolver/HanoiTowerSolverApp.java`
- `10-06-HanoiTowerSolver/Launcher.java`

Make sure the following commands work:
- mvn test
- mvn clean compile
- mvn javafx:run
without any errors.

The directory structure should be like this:
- `10-06-HanoiTowerSolver`
    - src
    - test
    - docs/
    - pom.xml
    - README.md: Include: Explain the logic of the app (short and sharp)
    - images/

Other requirements:
- Keep the code as simple as possible.
- Comment the code to explain the logic, for educational purposes.
- Use the same structure as the other projects in the repository (e.g `05-02-BinaryIO`)
- Use the same naming conventions as the other projects in the repository.
- Use the same documentation structure as the other projects in the repository.
- Use the same testing structure as the other projects in the repository.
- update <root-folder>/pom.xml to include the new project as a module


# Implement Evaluating Expressions

Save your response to `10-05-EvaluatingExpressions/`

Refer to the attachment for the logic of the evaluating expressions algorithm.
`EvaluateExpression.pdf`

Source code files include:
- `10-05-EvaluatingExpressions/EvaluateExpression.java`
- `10-05-EvaluatingExpressions/EvaluateExpressionApp.java`
- `10-05-EvaluatingExpressions/Launcher.java`

Make sure the following commands work:
- mvn test
- mvn clean compile
- mvn javafx:run
without any errors.

The directory structure should be like this:
- `10-05-EvaluatingExpressions`
    - src
    - test
    - docs/
    - pom.xml
    - README.md: Include: Explain the logic of the app (short and sharp)
    - images/

Other requirements:
- Keep the code as simple as possible.
- Comment the code to explain the logic, for educational purposes.
- Use the same structure as the other projects in the repository (e.g `05-02-BinaryIO`)
- Use the same naming conventions as the other projects in the repository.
- Use the same documentation structure as the other projects in the repository.
- Use the same testing structure as the other projects in the repository.
- update <root-folder>/pom.xml to include the new project as a module

---

# Implement External Sort

Save your response to `09-04-ExternalSort/`

Refer to the attachment for the logic of the external sort algorithm.
`External Sort.pdf`

Source code files include:
- `09-04-ExternalSort/CreateLargeFile.java`
- `09-04-ExternalSort/SortLargeFile.java`

Make sure the following commands work:
- mvn test
- mvn clean compile
- mvn javafx:run
without any errors.

The directory structure should be like this:
- `09-03-GenericSort`
    - src
    - test
    - docs/
    - pom.xml
    - README.md: Include: Explain the logic of the app (short and sharp)
    - images/

Other requirements:
- Keep the code as simple as possible.
- Comment the code to explain the logic, for educational purposes.
- Use the same structure as the other projects in the repository (e.g `05-02-BinaryIO`)
- Use the same naming conventions as the other projects in the repository.
- Use the same documentation structure as the other projects in the repository.
- Use the same testing structure as the other projects in the repository.
- update <root-folder>/pom.xml to include the new project as a module


---

# Implement Generic Sort

Save your response to `09-03-GenericSort/`


Make sure the following commands work:
- mvn test
- mvn clean compile
- mvn javafx:run
without any errors.

The directory structure should be like this:
- `09-03-GenericSort`
    - src
    - test
    - docs/
    - pom.xml
    - README.md: Include: Explain the logic of the app (short and sharp)
    - images/

Other requirements:
- Keep the code as simple as possible.
- Comment the code to explain the logic, for educational purposes.
- Use the same structure as the other projects in the repository (e.g `05-02-BinaryIO`)
- Use the same naming conventions as the other projects in the repository.
- Use the same documentation structure as the other projects in the repository.
- Use the same testing structure as the other projects in the repository.
- update <root-folder>/pom.xml to include the new project as a module

(Generic bubble sort) Write the following two generic methods using bubble
sort. The first method sorts the elements using the Comparable interface, and
the second uses the Comparator interface.

public static <E extends Comparable<E>>
void bubbleSort(E[] list)
public static <E> void bubbleSort(E[] list,
Comparator<? super E> comparator)

(Generic merge sort) Write the following two generic methods using merge sort.
The first method sorts the elements using the Comparable interface and the
second uses the Comparator interface.

public static <E extends Comparable<E>>
void mergeSort(E[] list)
public static <E> void mergeSort(E[] list,
Comparator<? super E> comparator)

(Generic quick sort) Write the following two generic methods using quick sort.
The first method sorts the elements using the Comparable interface, and the
second uses the Comparator interface.

public static <E extends Comparable<E>>
void quickSort(E[] list)
public static <E> void quickSort(E[] list,
Comparator<? super E> comparator)



# Implement a Bin Packing Problem

Save your response to `08-12-BinPacking/`

Write a program that find a solution for a bin packing problem and visualise the solution.

UI:
- Users can enter the number of items and the number of bins.
- Users can enter the weight of each item.
- Users can enter the capacity of each bin.
- Users can click "Load Sample Settings" button to load sample data. The sample data: Max cacapcity = 10. Max items = 10. All weights are integers between 1 and 10.
- The program has two tabs showcasing the two different algorithms: 
 1) Bin packing with smallest object first (First Fit)
 2) Optimal bin packing (Best Fit)
- Users can click "Find Solution" button to find a solution for the bin packing problem.
- Users can click "Clear" button to clear the settings.
- A canvas to display the bins and items visually.

User case is: 
- Users can enter the number of items and the number of bins.
- Users can enter the weight of each item.
- Users can enter the capacity of each bin.
- Or, users can click "Load Sample Settings" button to load sample data. The sample data: Max cacapcity = 10. Max items = 10.
- Users can click "Find Solution" button to find a solution for the bin packing problem.
- Users can click "Clear" button to clear the settings.

Write JUnit tests to check the logic of the app

Make sure the following commands work:
- mvn test
- mvn clean compile
- mvn javafx:run
without any errors.

The directory structure should be like this:
- `08-12-BinPacking`
    - src
    - test
    - docs/
    - pom.xml
    - README.md: Include: Explain the logic of the app (short and sharp)
    - images/

Other requirements:
- Keep the code as simple as possible.
- Comment the code to explain the logic, for educational purposes.
- Use the same structure as the other projects in the repository (e.g `05-02-BinaryIO`)
- Use the same naming conventions as the other projects in the repository.
- Use the same documentation structure as the other projects in the repository.
- Use the same testing structure as the other projects in the repository.
- update <root-folder>/pom.xml to include the new project as a module 
- Explain the logic of the largest block solver algorithm, including big O notation.

Additional requirements:
- What is the time complexity of your program? Include big O notation. Write it into doc files.
- Explain what algorithm you used for the bin packing problem, write it into doc files. Include the algorithm in the code.


---

# Implement a solver for Largest Block Problem

Save your response to `08-11-LargestBlock/`

Write a program that find a solution for a sudoku puzzle.

UI:
- Users can enter numbers 0, 1. Users can click on the number to toggle between 0 and 1.
- Default board size is 10x10. Max board size: 20x20. Users can change the board size by clicking the "Change Board Size" button.
- Button: Load a sample board (use the attachment)
- Button: Find a Solution
- Button: Clear
- Canvas: Display the solution

User case is: 
- Display a board with 0s and 1s.
- Users click the button to load sample board.
- User can enter numbers 0, 1. Users can click on the number to toggle between 0 and 1.
- User can click the "Find a Solution" button to find a solution for the board.
- User can click the "Clear" button to clear the board.

Write JUnit tests to check the logic of the app

Make sure the following commands work:
- mvn test
- mvn clean compile
- mvn javafx:run
without any errors.

The directory structure should be like this:
- `08-11-LargestBlock`
    - src
    - test
    - docs/
    - pom.xml
    - README.md: Include: Explain the logic of the app (short and sharp)
    - images/

Other requirements:
- Keep the code as simple as possible.
- Comment the code to explain the logic, for educational purposes.
- Use the same structure as the other projects in the repository (e.g `05-02-BinaryIO`)
- Use the same naming conventions as the other projects in the repository.
- Use the same documentation structure as the other projects in the repository.
- Use the same testing structure as the other projects in the repository.
- update <root-folder>/pom.xml to include the new project as a module 
- Explain the logic of the largest block solver algorithm, including big O notation.




----

# Implement a solver for sudoku puzzles

Save your response to `08-10-Sudoku/`

Write a program that find a solution for a sudoku puzzle.

UI:
- Users can enter numbers 1, 2, 3, 4, 5, 6, 7, 8, 9 or 0 (for empty cells)
- Button: Load a sample puzzle (use the attachment)
- Button: Find a Solution
- Button: Clear
- Canvas: Display the sudoku puzzle

User case is: 
- Display a sudoku puzzle with empty cells.
- User can enter numbers 1, 2, 3, 4, 5, 6, 7, 8, 9 or 0 (for empty cells)
- User can click the "Find a Solution" button to find a solution for the sudoku puzzle.
- User can click the "Clear" button to clear the sudoku puzzle.

Write JUnit tests to check the logic of the app

Make sure the following commands work:
- mvn test
- mvn clean compile
- mvn javafx:run
without any errors.

The directory structure should be like this:
- `08-10-Sudoku`
    - src
    - test
    - docs/
    - pom.xml
    - README.md: Include: Explain the logic of the app (short and sharp)
    - images/

Other requirements:
- Show only ONE solution for the sudoku puzzle.
- Keep the code as simple as possible.
- Comment the code to explain the logic, for educational purposes.
- Use the same structure as the other projects in the repository (e.g `05-02-BinaryIO`)
- Use the same naming conventions as the other projects in the repository.
- Use the same documentation structure as the other projects in the repository.
- Use the same testing structure as the other projects in the repository.
- update <root-folder>/pom.xml to include the new project as a module 
- Explain the logic of the sudoku solver algorithm, including big O notation.

Refer to the attachment for the logic of the sudoku solver UI.




----

# Finish `08-09-ConvexHull`

Write a program that implements a program that finds the convex hull of a set of points.

UI:
- Text input: Enter the number of points
- Button: Find Convex Hull
- Button: Add a point
- Button: Remove a point
- Canvas: Display the convex hull

Write JUnit tests to check the logic of the app

Make sure the following commands work:
- mvn test
- mvn clean compile
- mvn javafx:run
without any errors.

The directory structure should be like this:
- `08-09-ConvexHull`
    - src
    - test
    - docs/
    - pom.xml
    - README.md: Include: Explain the logic of the app (short and sharp)
    - images/

Other requirements:
- Keep the code as simple as possible.
- Comment the code to explain the logic, for educational purposes.
- Use the same structure as the other projects in the repository (e.g `05-02-BinaryIO`)
- Use the same naming conventions as the other projects in the repository.
- Use the same documentation structure as the other projects in the repository.
- Use the same testing structure as the other projects in the repository.
- update <root-folder>/pom.xml to include the new project as a module

Refer to the attachment for the logic of the convex hull algorithm.

---


# Migrate to Java25 and JavaFX25

- Modify `pom.xml` to use Java25 and JavaFX25
 - under root folder
- Modify `README.md` to include the new Java and JavaFX versions
- Modify `Prompt.md` to include the new Java and JavaFX versions
- Modify `INSTALL-DEV-ENV.md` to include the new Java and JavaFX versions
- Modify `COPILOT.md` to include the new Java and JavaFX versions
- Modify `.github/copilot-instructions.md` to include the new Java and JavaFX versions

make sure that:
mvn clean compile
without any errors.
for the root folder.

# Setup `.github/copilot-instructions.md`

Fetch the following from the internet:
Java Coding Guidelines: @Web  https://google.github.io/styleguide/javaguide.html

Update `.github/copilot-instructions.md` to include the guides above. 

Merge the existing policies with the guides above. 

`.github/copilot-instructions.md` will be used to guide the Copilot and Cursor to write code that follows the Java Coding Guidelines as well as the existing project policies. 

--- 


# tutorialspoint javafx todo


https://www.tutorialspoint.com/javafx/javafx_overview.htm


#JavaFX - An Introduction
JavaFX - Home
JavaFX - Overview
JavaFX - Environment
JavaFX - Installation Using Netbeans
JavaFX - Installation Using Eclipse
JavaFX - Installation using Visual Studio Code
JavaFX - Architecture
JavaFX - Application

# JavaFX 2D Shapes
JavaFX - 2D Shapes
JavaFX - Drawing a Line
JavaFX - Drawing a Rectangle
JavaFX - Drawing a Rounded Rectangle
JavaFX - Drawing a Circle
JavaFX - Drawing an Ellipse
JavaFX - Drawing a Polygon
JavaFX - Drawing a Polyline
JavaFX - Drawing a Cubic Curve
JavaFX - Drawing a Quad Curve
JavaFX - Drawing an Arc
JavaFX - Drawing an SVGPath

# JavaFX Properties of 2D Objects
JavaFX - Stroke Type Property
JavaFX - Stroke Width Property
JavaFX - Stroke Fill Property
JavaFX - Stroke Property
JavaFX - Stroke Line Join Property
JavaFX - Stroke Miter Limit Property
JavaFX - Stroke Line Cap Property
JavaFX - Smooth Property

#Operations on 2D Objects
JavaFX - 2D Shapes Operations
JavaFX - Union Operation
JavaFX - Intersection Operation
JavaFX - Subtraction Operation

# JavaFX Path Objects
JavaFX - Path Objects
JavaFX - LineTo Path Object
JavaFX - HLineTo Path Object
JavaFX - VLineTo Path Object
JavaFX - QuadCurveTo Path Object
JavaFX - CubicCurveTo Path Object
JavaFX - ArcTo Path Object

# JavaFX Color and Texture
JavaFX - Colors
JavaFX - Linear Gradient Pattern
JavaFX - Radial Gradient Pattern

# JavaFX Text
JavaFX - Text

# JavaFX Effects
JavaFX - Effects
JavaFX - Color Adjust Effect
JavaFX - Color input Effect
JavaFX - Image Input Effect
JavaFX - Blend Effect
JavaFX - Bloom Effect
JavaFX - Glow Effect
JavaFX - Box Blur Effect
JavaFX - GaussianBlur Effect
JavaFX - MotionBlur Effect
JavaFX - Reflection Effect
JavaFX - SepiaTone Effect
JavaFX - Shadow Effect
JavaFX - DropShadow Effect
JavaFX - InnerShadow Effect
JavaFX - Lighting Effect
JavaFX - Light.Distant Effect
JavaFX - Light.Spot Effect
JavaFX - Point.Spot Effect
JavaFX - DisplacementMap
JavaFX - PerspectiveTransform

# JavaFX Transformations
JavaFX - Transformations
JavaFX - Rotation Transformation
JavaFX - Scaling Transformation
JavaFX - Translation Transformation
JavaFX - Shearing Transformation

# JavaFX Animations
JavaFX - Animations
JavaFX - Rotate Transition
JavaFX - Scale Transition
JavaFX - Translate Transition
JavaFX - Fade Transition
JavaFX - Fill Transition
JavaFX - Stroke Transition
JavaFX - Sequential Transition
JavaFX - Parallel Transition
JavaFX - Pause Transition
JavaFX - Path Transition

# JavaFX Images
JavaFX - Images
JavaFX 3D Shapes

#  JavaFX - 3D Shapes
JavaFX - Creating a Box
JavaFX - Creating a Cylinder
JavaFX - Creating a Sphere

# Properties of 3D Objects
JavaFX - Cull Face Property
JavaFX - Drawing Modes Property
JavaFX - Material Property

# JavaFX Event Handling
JavaFX - Event Handling
JavaFX - Using Convenience Methods
JavaFX - Event Filters
JavaFX - Event Handlers

#  JavaFX UI Controls
JavaFX - UI Controls
JavaFX - ListView
JavaFX - Accordion
JavaFX - ButtonBar
JavaFX - ChoiceBox
JavaFX - HTMLEditor
JavaFX - MenuBar
JavaFX - Pagination
JavaFX - ProgressIndicator
JavaFX - ScrollPane
JavaFX - Separator
JavaFX - Slider
JavaFX - Spinner
JavaFX - SplitPane
JavaFX - TableView
JavaFX - TabPane
JavaFX - ToolBar
JavaFX - TreeView
JavaFX - Label
JavaFX - CheckBox
JavaFX - RadioButton
JavaFX - TextField
JavaFX - PasswordField
JavaFX - FileChooser
JavaFX - Hyperlink
JavaFX - Tooltip
JavaFX - Alert
JavaFX - DatePicker
JavaFX - TextArea

#  JavaFX Charts
JavaFX - Charts
JavaFX - Creating Pie Chart
JavaFX - Creating Line Chart
JavaFX - Creating Area Chart
JavaFX - Creating Bar Chart
JavaFX - Creating Bubble Chart
JavaFX - Creating Scatter Chart
JavaFX - Creating Stacked Area Chart
JavaFX - Creating Stacked Bar Chart

# JavaFX Layout Panes
JavaFX - Layout Panes
JavaFX - HBox Layout
JavaFX - VBox Layout
JavaFX - BorderPane Layout
JavaFX - StackPane Layout
JavaFX - TextFlow Layout
JavaFX - AnchorPane Layout
JavaFX - TilePane Layout
JavaFX - GridPane Layout
JavaFX - FlowPane Layout
JavaFX CSS
JavaFX - CSS

#  Media with JavaFX
JavaFX - Handling Media
JavaFX - Playing Video
JavaFX Useful Resources
JavaFX - Quick Guide
JavaFX - Useful Resources
JavaFX - Discussion



---

# Finish `07-13-Flat-Precedence-Calculator`

Write a program that implements a calculator that treats all operators (+−*/) have equal precedence.

```
Implement a calculator that treats
all operators (+−*/) have equal precedence. 

For example, 4 + 3 − 2 * 10
is 50.
```

UI:
- Text input: Enter the expression (e.g "4 + 3 − 2 * 10")
- Button: Evaluate
- Text area: Display the result
- Visualise the evaluation process

Write JUnit tests to check the logic of the app

Make sure the following commands work:
- mvn test
- mvn clean compile
- mvn javafx:run
without any errors.

The directory structure should be like this:
- `07-13-Flat-Precedence-Calculator`
    - src
    - test
    - docs/
    - pom.xml
    - README.md: Include: Explain the logic of the app (short and sharp)
    - images/

Other requirements:
- Keep the code as simple as possible.
- Comment the code to explain the logic, for educational purposes.
- Use the same structure as the other projects in the repository (e.g `05-02-BinaryIO`)
- Use the same naming conventions as the other projects in the repository.
- Use the same documentation structure as the other projects in the repository.
- Use the same testing structure as the other projects in the repository.
- update <root-folder>/pom.xml to include the new project as a module


---

# Finish `07-12-Postfix-Notation`

Write a program that combines colliding bouncing balls.

```
(Postfix notation) Postfix notation is a way of writing expressions without
using parentheses. For example, the expression (1 + 2) * 3 would be
written as 1 2 + 3 *. A postfix expression is evaluated using a stack. Scan
a postfix expression from left to right. A variable or constant is pushed into
the stack. When an operator is encountered, apply the operator with the top
two operands in the stack and replace the two operands with the result. The
following diagram shows how to evaluate 1 2 + 3 *.

Write a program to evaluate and visualise postfix expressions. 
Pass the expression from a text input
```

UI:
- Text input: Enter the postfix expression
- Button: Evaluate
- Text area: Display the result

Write JUnit tests to check the logic of the app

Make sure the following commands work:
- mvn test
- mvn clean compile
- mvn javafx:run
without any errors.

The directory structure should be like this:
- `07-12-Postfix-Notation`
    - src
    - test
    - docs/
    - pom.xml
    - README.md: Include: Explain the logic of the app (short and sharp)
    - images/

Other requirements:
- Keep the code as simple as possible.
- Comment the code to explain the logic, for educational purposes.
- Use the same structure as the other projects in the repository (e.g `05-02-BinaryIO`)
- Use the same naming conventions as the other projects in the repository.
- Use the same documentation structure as the other projects in the repository.
- Use the same testing structure as the other projects in the repository.
- update <root-folder>/pom.xml to include the new project as a module

---

# Finish `07-11-Hangman-Game`

Write a program that combines colliding bouncing balls.

```
(Game: hangman) Write a JavaFX GUI program that lets a user play the
game hangman. The user guesses a word by entering one letter at a time, as shown in the attached image. If the user misses seven times, a hanging man swings. Once
a word is finished, the user can press the Enter key to continue to guess
another word.
```


UI:
- Guess a word: ....
- Missed letters: ....
- As similar as possible to the attached image

Write JUnit tests to check the logic of the app

Make sure the following commands work:
- mvn test
- mvn clean compile
- mvn javafx:run
without any errors.

The directory structure should be like this:
- `07-11-Hangman-Game`
    - src
    - test
    - docs/
    - pom.xml
    - README.md: Include: Explain the logic of the app (short and sharp)
    - images/

Other requirements:
- Keep the code as simple as possible.
- Comment the code to explain the logic, for educational purposes.
- Use the same structure as the other projects in the repository (e.g `05-02-BinaryIO`)
- Use the same naming conventions as the other projects in the repository.
- Use the same documentation structure as the other projects in the repository.
- Use the same testing structure as the other projects in the repository.
- update <root-folder>/pom.xml to include the new project as a module


---


# Finish `07-10-Combine-Colliding-Bouncing-Balls`

Write a program that combines colliding bouncing balls.

```
(Combine colliding bouncing balls) The example in Section 20.8 displays
multiple bouncing balls. Extend the example to detect collisions. Once two
balls collide, remove the later ball that was added to the pane and add its
radius to the other ball, as shown in Figure 20.17b. Use the Suspend button
to suspend the animation, and the Resume button to resume the anima-
tion. Add a mouse-pressed handler that removes a ball when the mouse is
pressed on the ball.
```

Please finish the project by adding the following features:
- Detect collisions between bouncing balls
- Remove the later ball that was added to the pane and add its radius to the other ball
- Use the Suspend button to suspend the animation
- Use the Resume button to resume the animation
- Add a mouse-pressed handler that removes a ball when the mouse is pressed on the ball

UI:
- a pane to display the bouncing balls
- a button to suspend the animation. label: Suspend
- a button to resume the animation. label: Resume
- a button to add a ball. label: +
- a button to remove a ball. label: -

Write JUnit tests to check the logic of the app

Make sure the following commands work:
- mvn test
- mvn clean compile
- mvn javafx:run
without any errors.

The directory structure should be like this:
- `07-10-Combine-Colliding-Bouncing-Balls`
    - src
    - test
    - docs/
    - pom.xml
    - README.md: Include: Explain the logic of the app (short and sharp)
    - images/

Other requirements:
- Keep the code as simple as possible.
- Comment the code to explain the logic, for educational purposes.
- Use the same structure as the other projects in the repository (e.g `05-02-BinaryIO`)
- Use the same naming conventions as the other projects in the repository.
- Use the same documentation structure as the other projects in the repository.
- Use the same testing structure as the other projects in the repository.
- update <root-folder>/pom.xml to include the new project as a module


---

# Finish `07-09-Store-Numbers-in-a-Linked-List`

The purpose of this project is to solve the Eight Queens problem and animate the solution.

```
(Store numbers in a linked list) Write a program that lets the user enter num-
bers from a graphical user interface and displays them in a text area, as shown
in attached image. Use a linked list to store the numbers. Do not store dupli-
cate numbers. Add the buttons Sort, Shuffle, and Reverse to sort, shuffle, and
reverse the list.
```

Please finish the project by adding the following features:
- Enter a number in the text field
- Sort: Sort the numbers in the linked list
- Shuffle: Shuffle the numbers in the linked list
- Reverse: Reverse the numbers in the linked list

Write JUnit tests to check the logic of the Store Numbers in a Linked List problem.

Make sure the following commands work:
- mvn test
- mvn clean compile
- mvn javafx:run
without any errors.

The directory structure should be like this:
- `07-09-Store-Numbers-in-a-Linked-List`
    - src
    - test
    - docs/
    - pom.xml
    - README.md: Include: Display the Java code snippet that stores numbers in a linked list (short and sharp)
    - images/

Other requirements:
- Keep the code as simple as possible.
- Comment the code to explain the logic, for educational purposes.
- Use the same structure as the other projects in the repository (e.g `05-02-BinaryIO`)
- Use the same naming conventions as the other projects in the repository.
- Use the same documentation structure as the other projects in the repository.
- Use the same testing structure as the other projects in the repository.



---



---

# Finish `06-07-Eight-Queens`

The purpose of this project is to solve the Eight Queens problem and animate the solution.

```
(Game: Eight Queens) The Eight Queens problem is to find a solution to place
a queen in each row on a chessboard such that no two queens can attack each
other. Write a program to solve the Eight Queens problem using recursion and
display the result
```

Please finish the project by adding the following features:
- Diplay an input box: Enter the size of the chessboard. This be an integer between N = 1 and N = 16. Default is N = 8. Size of the chessboard is N x N.
- Display a button: Solve the Eight Queens problem. When users click the button, the Eight Queens problem will be solved.
- Display the Eight Queens solution with animation.

The animation should be like this:
- Place the first queen. Display the queen as "♕". Indicate all the positions that the queen can attack.
- Place the second queen. Display the queen as "♕". Indicate all the positions that the queen can attack.
- Place the third queen. Display the queen as "♕". Indicate all the positions that the queen can attack.
- and so on until all the queens are placed.

Stop the animation as soon as a solution is found.

Change the UI 
- Change the size of the window to fit the chessboard.


Write JUnit tests to check the logic of the Eight Queens problem.

Make sure the following commands work:
- mvn test
- mvn clean compile
- mvn javafx:run
without any errors.

The directory structure should be like this:
- `06-07-Eight-Queens`
    - src
    - test
    - docs/
    - pom.xml
    - README.md: Include: Display the Java code snippet that solves the Eight Queens problem (short and sharp)
    - images/

Other requirements:
- Keep the code as simple as possible.
- Comment the code to explain the logic, for educational purposes.
- Use the same structure as the other projects in the repository (e.g `05-02-BinaryIO`)
- Use the same naming conventions as the other projects in the repository.
- Use the same documentation structure as the other projects in the repository.
- Use the same testing structure as the other projects in the repository.



---


# Finish `06-20-Recursion-Snipplets-Collection`

Save your code in the `06-20-Recursion-Snipplets-Collection` folder.

The structure of the folder should be like this:
- `README.md`: List of all the problems
- `Problem-XX-<problem-name>.md`: 
 - `XX`: The problem number, e.g 01, 02, 03, ...
 - State the problem
 - Give the solution in Java as a code snippet

Important notes: 
- Only give the code snippet, no need to give the full code.
- Make it as simple as possible.
- Comment the code to explain the logic, for educational purposes. Short and sharp.

The problems are:

Problem 1: 

Write a recursive method public static void dec2b(double x,int b,
int n) that displays x, where 0 ≤ x < 1 in base b with at most n digits after
the decimal point. For instance, dec2b(0.625,2,10) should return 0.101, and
dec2b(0.625,3,10) should return 0.1212121212


Problem 2: 

Write a recursive method public static void randomFillSortedArray
(int[] x, int l, int r, int a, int b) that fills the array x between l
and r with random values between a and b such that x is sorted. Here is a sample
run:

Enter the array size: 10
Enter the limits: 0 1000
[235, 280, 382, 428, 458, 462, 484, 495, 536, 850]
Enter the array size: 10
Enter the limits: 0 9
[0, 0, 3, 3, 4, 6, 6, 8, 8, 9]

Problem 3

Give the code: 

```java
f0 = 0; // For fib(0)
f1 = 1; // For fib(1)
for (int i = 1; i <= n; i++) {
currentFib = f0 + f1;
f0 = f1;
f1 = currentFib;
}
// After the loop, currentFib is fib(n)
```

modify the code above so that the program finds the number of times the fib method is called. (Hint: Use a static
variable and increment it every time the method is called.)

Problem 4: 

(Print the characters in a string reversely) Write a recursive method that dis-
plays a string reversely on the console using the following header:
public static void reverseDisplay(String value)
For example, reverseDisplay("abcd") displays dcba. Write a test program
that prompts the user to enter a string and displays its reversal.

Problem 5:

(Occurrences of a specified character in a string) Write a recursive method that
finds the number of occurrences of a specified letter in a string using the follow-
ing method header:
public static int count(String str, char a)
For example, count("Welcome", 'e') returns 2. Write a test program that
prompts the user to enter a string and a character, and displays the number of
occurrences for the character in the string.

Problem 6:

Write a recursive method that displays all permutations of a given array of
integers. Here is a sample run:

Enter the array size : 3
[1, 2, 3]
[1, 3, 2]
[2, 1, 3]
[2, 3, 1]
[3, 1, 2]
[3, 2, 1]




---

# Finish `06-06-Hilbert-Curve`

The purpose of this project is to display the Hilbert curve.

```
(Hilbert curve) The Hilbert curve, first described by German mathematician
David Hilbert in 1891, is a space-filling curve that visits every point in a square
grid with a size of 2 * 2, 4 * 4, 8 * 8, 16 * 16, or any other power of 2.
Write a program that displays a Hilbert curve for the specified order, as shown
in Figure 18.19.
```

Please finish the project by adding the following features:
- Diplay an input box: Enter the order of the Hilbert curve. This be an integer between 1 and 6.
- Display a button: Draw the Hilbert curve. When users click the button, the Hilbert curve will be drawn.
- Display the Hilbert curve.

Change the UI 

- Fix the width of the window to 700 pixels
- Fix the width * height of the area showing the Hilbert curve to 550 * 500 

Write JUnit tests to check the logic of the Hilbert curve.

Make sure the following commands work:
- mvn test
- mvn clean compile
- mvn javafx:run
without any errors.

The directory structure should be like this:
- `06-06-Hilbert-Curve`
    - src
    - test
    - docs/
    - pom.xml
    - README.md
    - images/

Other requirements:
- Keep the code as simple as possible.
- Comment the code to explain the logic, for educational purposes.
- Use the same structure as the other projects in the repository (e.g `05-02-BinaryIO`)
- Use the same naming conventions as the other projects in the repository.
- Use the same documentation structure as the other projects in the repository.
- Use the same testing structure as the other projects in the repository.



---



# Finish `06-05-KnightTour`

The purpose of this project is to display the Koch snowflake fractal.

```
(Game: Knight’s Tour animation) Write a program for the Knight’s Tour problem.
Your program should let the user move a knight to any starting square and click the
Solve button to animate a knight moving along the path, as shown in Figure 18.16.
```

Please finish the project by adding the following features:
- Display a chess board
- Users can move the knight to any starting square
- Add a button to move the knight to any starting square
- Show the path of the knight
- Show the knight as "♘"
- Add a "Solve" button to animate the knight moving along the path
- When users click the "Solve" button, the knight will move along the path

Write JUnit tests to check the logic of the knight's tour.

List of end-to-end testing scenarios:
- 

Make sure the following commands work:
- mvn test
- mvn clean compile
- mvn javafx:run
without any errors.

The directory structure should be like this:
- `06-05-KnightTour`
    - src
    - test
    - docs/
    - pom.xml
    - README.md
    - images/

Other requirements:
- Keep the code as simple as possible.
- Comment the code to explain the logic, for educational purposes.
- Use the same structure as the other projects in the repository (e.g `05-02-BinaryIO`)
- Use the same naming conventions as the other projects in the repository.
- Use the same documentation structure as the other projects in the repository.
- Use the same testing structure as the other projects in the repository.



---


# Finish `06-04-KochSnowflake`

The purpose of this project is to display the Koch snowflake fractal.

```
(Koch snowflake fractal) The text presented the Sierpinski triangle fractal. In
this exercise, you will write a program to display another fractal, called the Koch
snowflake, named after a famous Swedish mathematician. A Koch snowflake is
created as follows:
1. Begin with an equilateral triangle, which is considered to be the Koch fractal
of order (or level) 0, as shown in Figure 18.14a.
2. Divide each line in the shape into three equal line segments and draw an out-
ward equilateral triangle with the middle line segment as the base to create a
Koch fractal of order 1, as shown in Figure 18.14b.
3. Repeat Step 2 to create a Koch fractal of order 2, 3, . . . , and so on, as shown
in Figures 18.14c and d.
```

Please finish the project by adding the following features:
- Add a slider to control the order of the Koch snowflake
- Add a button to clear the fractal

Initial size of the fractal is 500x500 pixels.

Write JUnit tests to check the Koch snowflake fractal logic.

Make sure the following commands work:
- mvn test
- mvn clean compile
- mvn javafx:run
without any errors.

The directory structure should be like this:
- `06-04-KochSnowflake`
    - src
    - test
    - docs/
    - pom.xml
    - README.md
    - images/

Other requirements:
- Keep the code as simple as possible.
- Comment the code to explain the logic, for educational purposes.
- Use the same structure as the other projects in the repository (e.g `05-02-BinaryIO`)
- Use the same naming conventions as the other projects in the repository.
- Use the same documentation structure as the other projects in the repository.
- Use the same testing structure as the other projects in the repository.



---

# Finish `06-03-RecursiveZeroFinder`

The purpose of this project is to find the root of a function using recursive binary search.

```
Let f(x) be a function. Let l, r, and e be real numbers. Write a recursive method that
computes and returns a value v such that |f(v)| <= e, and l <= v <= r. That is, a
method that finds an approximation of a zero of f(x) between l and r. We assume that
f(l)f(r) <= 0.
```

Please finish the project by adding the following features:
- Add text fields for the input: f(x), l, r, e

e.g
f(x) = x^2 - 4, l = 0, r = 4, e = 0.001

- Add a button to clear the input and result
- Add a button to exit the application

Write JUnit tests to check the zero finding logic.

Make sure the following commands work:
- mvn test
- mvn clean compile
- mvn javafx:run
without any errors.

The directory structure should be like this:
- 06-03-RecursiveZeroFinder
    - src
    - test
    - docs/
    - pom.xml
    - README.md
    - images/

Other requirements:
- Keep the code as simple as possible.
- Comment the code to explain the logic, for educational purposes.
- Use the same structure as the other projects in the repository (e.g `05-02-BinaryIO`)
- Use the same naming conventions as the other projects in the repository.
- Use the same documentation structure as the other projects in the repository.
- Use the same testing structure as the other projects in the repository.

----------------------------------------------------------

# Finish `06-02-Recursion-Check-Anagrams`

The purpose of this project is to check if two words are anagrams of each other.

```
(Identifying anagrams) Two words are anagrams of each other if they contain the
same letters that are arranged in different orders. Write a recursive method that
can identify if two given words are anagrams of each other.
```

This project is currently a Hello World project.

Please finish the project by adding the following features:
- Add a text field for the input string: Word 1
- Add a text field for the input string: Word 2
- Add a button to check if the input string is an anagram: Check Anagram

- Add a label to display the result
- Add a button to clear the input and result
- Add a button to exit the application

Write JUnit tests to check the anagram checking logic.

Make sure the following commands work:
- mvn test
- mvn clean compile
- mvn javafx:run
without any errors.

The directory structure should be like this:
- 06-02-Recursion-Check-Anagrams
    - src
    - test
    - docs/
    - pom.xml
    - README.md
    - images/

Other requirements:
- Keep the code as simple as possible.
- Comment the code to explain the logic, for educational purposes.
- Use the same structure as the other projects in the repository (e.g `05-02-BinaryIO`)
- Use the same naming conventions as the other projects in the repository.
- Use the same documentation structure as the other projects in the repository.
- Use the same testing structure as the other projects in the repository.

----------------------------------------------------------

# Add screenshots to the README.md

Add screenshots of the JavaFX application to the `README.md` file in the sub-folder of each project.

For example, in `01-01-JavaFX-HelloWorld/README.md`, add screenshots of the Hello World application. Location of the screenshots: `01-01-JavaFX-HelloWorld/images/`

The screenshots have been added to `README.md` for the following projects:
- 01-01-JavaFX-HelloWorld
- 01-02-JavaFX.Button
- ...
- 08-01-LinearSearch
- 08-02-BinarySearch

Pls add screenshots for the remaining projects in the same way.
- 08-03-ClosestPair
- 08-04-SelectionNewSort
- ...
- 12-03-MapHash

# Run .jar files for all sub-projects 

We have implemented bash scripts to run .jar files under two folders:

```

01-01-JavaFX-HelloWorld/run-jar.sh
01-01-JavaFX-HelloWorld/run.sh

01-02-JavaFX.Button/run.sh
01-02-JavaFX.Button/run-simple.sh
```

Please make sure that:
- `mvn package`
- .jar files are successfully created 
- .jar files are able to run

for the rest of the sub-projects (sub-folders) (0x-0x-zzzzzzzz)
(01-03-Panes.UI.Controls.Shapes, 01-04-NodeStyleRotateDemo,...)

by creating 
- 0x-0x-zzzzzzzz/run.sh
- 0x-0x-zzzzzzzz/run-jar.sh

refer to `pom.xml` under the root folder for the list of maven `<modules>` we want to package the jar file. 

# Add missing `architecture.md` and `concepts.md` to each sub-folder 

The following sub-folders are missing `architecture.md` and `concepts.md`: 
- 01-01-JavaFX-HelloWorld
- 01-02-JavaFX.Button
- 01-03-Panes.UI.Controls.Shapes
- 01-04-NodeStyleRotateDemo

Use the structures of `architecture.md`, `concepts.md` similar to the sample files below: 

/Users/vuhung/00.Work/02.ACU/github/ITEC313/JavaFX/01-05-MoreShapes/docs/architecture.md
/Users/vuhung/00.Work/02.ACU/github/ITEC313/JavaFX/01-05-MoreShapes/docs/concepts.md

# Create a pom.xml file at the top folder

- with targets: clean, compile
- `clean` will `clean` all 0x-0y-zzz sub-folders 
- `compile` will `compile` all 0x-0y-zzz sub-folders 

# Generate `README.md` at the top folder

The structure of the file should be 

- Overview of the project 
- Overview of each sub-folder 
- Who are target audience 
- How to use this repo to learn, in which order, time-required to learn 
- What exercies should be done by learners 
- The structures of sub folder (0x-0x-zzzzz)

# Create INSTALL-DEV-ENV.md 
and briefly how to install the development environment (java, maven...) on Mac OS, Linux/Ubutun, Linux/Redhat, Windows 11 