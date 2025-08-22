package com.acu.car.management.controller;


import com.acu.car.management.CarManagementApp;
import com.acu.car.management.persistence.repository.CarRepository;
import com.acu.car.management.persistence.model.Car;
import com.acu.car.management.persistence.model.Component;
import com.zaxxer.hikari.HikariDataSource;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;


import java.io.IOException;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.StreamSupport;

/**
 * Main controller for the car management overview screen
 * Handles car and component management, search, and CRUD operations
 */
public class OverviewController {
    // Observable lists for UI data binding
    private final ObservableList<Car> cars = FXCollections.observableArrayList();
    private final ObservableList<Component> components = FXCollections.observableArrayList();
    
    // UI components
    @FXML
    private TableView<Component> tvComponents;
    @FXML
    private TableView<Car> tvCars;
    @FXML
    private TextField tfSearch;
    
    // Repository for database operations
    private CarRepository carRepository;

    /**
     * Initialize the controller with database connection and load existing cars
     */
    public void initDataSource(HikariDataSource hikariDataSource) {
        this.carRepository = new CarRepository(hikariDataSource);
        Iterable<Car> carsFound = carRepository.findAll();
        cars.addAll(StreamSupport.stream(carsFound.spliterator(), false).toList());
    }

    /**
     * Initialize the UI components and table views
     */
    public void initialize() {
        initializeTableViewCars();
        initializeTableViewComponents();
    }

    /**
     * Setup the cars table view with editable columns and search functionality
     */
    private void initializeTableViewCars() {
        // Create table columns
        TableColumn<Car, String> make = new TableColumn<>("Make");
        TableColumn<Car, String> model = new TableColumn<>("Model");
        TableColumn<Car, Integer> year = new TableColumn<>("Year");
        TableColumn<Car, String> color = new TableColumn<>("Color");
        TableColumn<Car, Double> mileage = new TableColumn<>("Mileage");
        TableColumn<Car, String> category = new TableColumn<>("Category");

        // Configure make column
        make.setPrefWidth(150);
        make.setCellValueFactory(new PropertyValueFactory<>("make"));
        make.setCellFactory(TextFieldTableCell.forTableColumn());
        make.setOnEditCommit(event -> {
            Car selectedCar = event.getRowValue();
            selectedCar.setMake(event.getNewValue());
            carRepository.save(selectedCar);
        });

        // Configure model column
        model.setPrefWidth(150);
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        model.setCellFactory(TextFieldTableCell.forTableColumn());
        model.setOnEditCommit(event -> {
            Car selectedCar = event.getRowValue();
            selectedCar.setModel(event.getNewValue());
            carRepository.save(selectedCar);
        });

        // Configure year column
        year.setPrefWidth(100);
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        year.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        year.setOnEditCommit(event -> {
            Car selectedCar = event.getRowValue();
            selectedCar.setYear(event.getNewValue());
            carRepository.save(selectedCar);
        });

        // Configure color column
        color.setPrefWidth(100);
        color.setCellValueFactory(new PropertyValueFactory<>("color"));
        color.setCellFactory(TextFieldTableCell.forTableColumn());
        color.setOnEditCommit(event -> {
            Car selectedCar = event.getRowValue();
            selectedCar.setColor(event.getNewValue());
            carRepository.save(selectedCar);
        });

        // Configure mileage column
        mileage.setPrefWidth(100);
        mileage.setCellValueFactory(new PropertyValueFactory<>("mileage"));
        mileage.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        mileage.setOnEditCommit(event -> {
            Car selectedCar = event.getRowValue();
            selectedCar.setMileage(event.getNewValue());
            carRepository.save(selectedCar);
        });

        // Configure category column with dropdown
        category.setPrefWidth(150);
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        category.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(CarManagementApp.carTypes)));
        category.setOnEditCommit(event -> {
            Car selectedCar = event.getRowValue();
            selectedCar.setCategory(event.getNewValue());
            carRepository.save(selectedCar);
        });

        // Setup filtering and sorting
        FilteredList<Car> filteredList = new FilteredList<>(cars, car -> true);
        SortedList<Car> sortedList = new SortedList<>(filteredList.sorted(Comparator.comparing(Car::getMake).thenComparing(Car::getModel)));
        sortedList.comparatorProperty().bind(tvCars.comparatorProperty());
        tvCars.setItems(sortedList);
        tvCars.getColumns().addAll(make, model, year, color, mileage, category);
        tvCars.setEditable(true);
        tvCars.setTableMenuButtonVisible(true);

        // Handle car selection to show components
        tvCars.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Car>) change -> {
            if (Objects.nonNull(change.getList().get(0).getComponents())) {
                components.setAll(change.getList().get(0).getComponents());
            } else {
                components.clear();
            }
        });

        // Setup search functionality
        tfSearch.textProperty().addListener(obs -> {
            String filter = tfSearch.getText();
            if (filter == null || filter.isEmpty()) {
                filteredList.setPredicate(car -> true);
            } else {
                filteredList.setPredicate(car -> car.getMake().toLowerCase().contains(filter.toLowerCase()) || 
                                               car.getModel().toLowerCase().contains(filter.toLowerCase()));
            }
        });
    }

    /**
     * Setup the components table view with editable columns
     */
    private void initializeTableViewComponents() {
        // Create table columns
        TableColumn<Component, String> code = new TableColumn<>("Code");
        TableColumn<Component, String> description = new TableColumn<>("Description");
        TableColumn<Component, Double> cost = new TableColumn<>("Cost ($)");

        // Configure code column
        code.setPrefWidth(300);
        code.setCellValueFactory(new PropertyValueFactory<>("componentCode"));
        code.setCellFactory(TextFieldTableCell.forTableColumn());
        code.setOnEditCommit(event -> {
            Component selectedComponent = event.getRowValue();
            selectedComponent.setComponentCode(event.getNewValue());
            carRepository.save(selectedComponent.getCar());
        });

        // Configure description column
        description.setPrefWidth(300);
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        description.setCellFactory(TextFieldTableCell.forTableColumn());
        description.setOnEditCommit(event -> {
            Component selectedComponent = event.getRowValue();
            selectedComponent.setDescription(event.getNewValue());
            carRepository.save(selectedComponent.getCar());
        });

        // Configure cost column
        cost.setPrefWidth(150);
        cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        cost.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        cost.setOnEditCommit(event -> {
            Component selectedComponent = event.getRowValue();
            selectedComponent.setCost(event.getNewValue());
            carRepository.save(selectedComponent.getCar());
        });

        tvComponents.getColumns().addAll(code, description, cost);
        tvComponents.setItems(components);
    }

    /**
     * Handle quit button click - exit the application
     */
    @FXML
    void onQuitClicked() {
        Platform.exit();
    }

    /**
     * Handle add car button click - show dialog and save new car
     */
    @FXML
    void onAddCarClicked() throws IOException {
        new AddCarDialog().showAndWait().ifPresent(car -> {
            try {
                Car saved = carRepository.save(car);
                cars.add(saved);
            } catch (RuntimeException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
            }
        });
    }

    /**
     * Handle remove car button click - delete selected car
     */
    @FXML
    void onRemoveCarClicked() {
        Car selectedItem = tvCars.getSelectionModel().getSelectedItem();
        if (Objects.nonNull(selectedItem)) {
            try {
                carRepository.deleteById(selectedItem.getId());
                cars.remove(selectedItem);
            } catch (RuntimeException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
            }
        }
    }

    /**
     * Handle add component button click - show dialog and save new component
     */
    @FXML
    void onAddComponentClicked() throws IOException {
        Car selectedCar = tvCars.getSelectionModel().getSelectedItem();
        if (Objects.nonNull(selectedCar)) {
            new AddComponentDialog().showAndWait().ifPresent(component -> {
                try {
                    components.add(component);
                    selectedCar.addComponent(component);
                    carRepository.save(selectedCar);
                } catch (RuntimeException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
                }
            });
        }
    }

    /**
     * Handle remove component button click - delete selected component
     */
    @FXML
    void onRemoveComponentClicked() {
        Car selectedCar = tvCars.getSelectionModel().getSelectedItem();
        Component selectedComponent = tvComponents.getSelectionModel().getSelectedItem();
        if (Objects.nonNull(selectedCar) && Objects.nonNull(selectedComponent)) {
            try {
                components.remove(selectedComponent);
                selectedCar.removeComponent(selectedComponent);
                carRepository.save(selectedCar);
            } catch (RuntimeException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
            }
        }
    }

    /**
     * Handle about button click - show application information
     */
    @FXML
    void onAboutClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Car Management System");
        alert.setContentText("""
                Author:
                ACU Car Management Team
                        
                version 1.0
                """);
        alert.showAndWait();
    }
}
