package com.acu.javafx.database;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * JavaFX Employee Database Management Application
 * Demonstrates CRUD operations with SQLite database
 */
public class EmployeeDatabaseApp extends Application {
    
    private TableView<Employee> tableView;
    private TextField nameField, emailField, departmentField, salaryField, searchField;
    private ObservableList<Employee> employeeList;
    private Employee selectedEmployee;
    
    @Override
    public void start(Stage primaryStage) {
        // Initialize database
        DatabaseManager.initializeDatabase();
        DatabaseManager.populateSampleData();
        
        // Initialize employee list
        employeeList = FXCollections.observableArrayList();
        refreshEmployeeList();
        
        // Create UI components
        createUI();
        
        // Set up scene
        Scene scene = new Scene(createMainLayout(), 900, 600);
        primaryStage.setTitle("Employee Database Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void createUI() {
        // Create table view
        tableView = new TableView<>();
        tableView.setEditable(false);
        tableView.setPrefHeight(300);
        
        // Create table columns
        TableColumn<Employee, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(50);
        
        TableColumn<Employee, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(150);
        
        TableColumn<Employee, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setPrefWidth(200);
        
        TableColumn<Employee, String> deptCol = new TableColumn<>("Department");
        deptCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        deptCol.setPrefWidth(120);
        
        TableColumn<Employee, Double> salaryCol = new TableColumn<>("Salary");
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        salaryCol.setPrefWidth(100);
        salaryCol.setCellFactory(col -> new TableCell<Employee, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", item));
                }
            }
        });
        
        tableView.getColumns().addAll(idCol, nameCol, emailCol, deptCol, salaryCol);
        tableView.setItems(employeeList);
        
        // Handle row selection
        tableView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                selectedEmployee = newValue;
                if (newValue != null) {
                    populateFields(newValue);
                } else {
                    clearFields();
                }
            }
        );
    }
    
    private VBox createMainLayout() {
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_CENTER);
        
        // Title
        Label titleLabel = new Label("Employee Database Management System");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        // Search section
        HBox searchBox = createSearchSection();
        
        // Table section
        VBox tableBox = new VBox(10);
        tableBox.getChildren().addAll(
            new Label("Employee List:"),
            tableView
        );
        
        // Form section
        VBox formBox = createFormSection();
        
        // Button section
        HBox buttonBox = createButtonSection();
        
        mainLayout.getChildren().addAll(
            titleLabel,
            searchBox,
            tableBox,
            formBox,
            buttonBox
        );
        
        return mainLayout;
    }
    
    private HBox createSearchSection() {
        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        
        Label searchLabel = new Label("Search by name:");
        searchField = new TextField();
        searchField.setPrefWidth(200);
        searchField.setPromptText("Enter name to search...");
        
        Button searchBtn = new Button("Search");
        searchBtn.setOnAction(e -> searchEmployees());
        
        Button clearSearchBtn = new Button("Clear");
        clearSearchBtn.setOnAction(e -> {
            searchField.clear();
            refreshEmployeeList();
        });
        
        searchBox.getChildren().addAll(searchLabel, searchField, searchBtn, clearSearchBtn);
        return searchBox;
    }
    
    private VBox createFormSection() {
        VBox formBox = new VBox(10);
        formBox.setAlignment(Pos.TOP_LEFT);
        
        Label formLabel = new Label("Employee Information:");
        formLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        // Create form fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));
        
        nameField = new TextField();
        nameField.setPromptText("Enter name");
        
        emailField = new TextField();
        emailField.setPromptText("Enter email");
        
        departmentField = new TextField();
        departmentField.setPromptText("Enter department");
        
        salaryField = new TextField();
        salaryField.setPromptText("Enter salary");
        
        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(emailField, 1, 1);
        grid.add(new Label("Department:"), 0, 2);
        grid.add(departmentField, 1, 2);
        grid.add(new Label("Salary:"), 0, 3);
        grid.add(salaryField, 1, 3);
        
        formBox.getChildren().addAll(formLabel, grid);
        return formBox;
    }
    
    private HBox createButtonSection() {
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button addBtn = new Button("Add Employee");
        addBtn.setOnAction(e -> addEmployee());
        
        Button updateBtn = new Button("Update Employee");
        updateBtn.setOnAction(e -> updateEmployee());
        
        Button deleteBtn = new Button("Delete Employee");
        deleteBtn.setOnAction(e -> deleteEmployee());
        
        Button clearBtn = new Button("Clear Form");
        clearBtn.setOnAction(e -> clearFields());
        
        Button refreshBtn = new Button("Refresh List");
        refreshBtn.setOnAction(e -> refreshEmployeeList());
        
        buttonBox.getChildren().addAll(addBtn, updateBtn, deleteBtn, clearBtn, refreshBtn);
        return buttonBox;
    }
    
    private void addEmployee() {
        try {
            Employee employee = new Employee();
            employee.setName(nameField.getText().trim());
            employee.setEmail(emailField.getText().trim());
            employee.setDepartment(departmentField.getText().trim());
            employee.setSalary(Double.parseDouble(salaryField.getText().trim()));
            
            if (employee.getName().isEmpty()) {
                showAlert("Error", "Name is required!");
                return;
            }
            
            if (DatabaseManager.insertEmployee(employee)) {
                showAlert("Success", "Employee added successfully!");
                refreshEmployeeList();
                clearFields();
            } else {
                showAlert("Error", "Failed to add employee!");
            }
        } catch (NumberFormatException ex) {
            showAlert("Error", "Please enter a valid salary amount!");
        }
    }
    
    private void updateEmployee() {
        if (selectedEmployee == null) {
            showAlert("Error", "Please select an employee to update!");
            return;
        }
        
        try {
            selectedEmployee.setName(nameField.getText().trim());
            selectedEmployee.setEmail(emailField.getText().trim());
            selectedEmployee.setDepartment(departmentField.getText().trim());
            selectedEmployee.setSalary(Double.parseDouble(salaryField.getText().trim()));
            
            if (selectedEmployee.getName().isEmpty()) {
                showAlert("Error", "Name is required!");
                return;
            }
            
            if (DatabaseManager.updateEmployee(selectedEmployee)) {
                showAlert("Success", "Employee updated successfully!");
                refreshEmployeeList();
                clearFields();
            } else {
                showAlert("Error", "Failed to update employee!");
            }
        } catch (NumberFormatException ex) {
            showAlert("Error", "Please enter a valid salary amount!");
        }
    }
    
    private void deleteEmployee() {
        if (selectedEmployee == null) {
            showAlert("Error", "Please select an employee to delete!");
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Delete Employee");
        alert.setContentText("Are you sure you want to delete " + selectedEmployee.getName() + "?");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (DatabaseManager.deleteEmployee(selectedEmployee.getId())) {
                    showAlert("Success", "Employee deleted successfully!");
                    refreshEmployeeList();
                    clearFields();
                } else {
                    showAlert("Error", "Failed to delete employee!");
                }
            }
        });
    }
    
    private void searchEmployees() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            refreshEmployeeList();
            return;
        }
        
        employeeList.clear();
        employeeList.addAll(DatabaseManager.searchEmployeesByName(searchTerm));
    }
    
    private void refreshEmployeeList() {
        employeeList.clear();
        employeeList.addAll(DatabaseManager.getAllEmployees());
    }
    
    private void populateFields(Employee employee) {
        nameField.setText(employee.getName());
        emailField.setText(employee.getEmail());
        departmentField.setText(employee.getDepartment());
        salaryField.setText(String.valueOf(employee.getSalary()));
    }
    
    private void clearFields() {
        nameField.clear();
        emailField.clear();
        departmentField.clear();
        salaryField.clear();
        selectedEmployee = null;
        tableView.getSelectionModel().clearSelection();
    }
    
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
