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