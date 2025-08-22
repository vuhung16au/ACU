package com.acu.javafx.products.controller;

import com.acu.javafx.products.persistence.repository.ProductRepository;
import com.acu.javafx.products.persistence.model.Product;
import com.zaxxer.hikari.HikariDataSource;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * JavaFX controller for the products overview screen.
 *
 * Initializes table columns, binds sorting and filtering, and wires UI
 * actions to persistence through {@link ProductRepository}.
 */
public class OverviewController {
    @FXML private ComboBox<String> cbCategory;
    @FXML private TextField tfDescription;
    @FXML private TextField tfPrice;
    @FXML private TextField tfName;
    @FXML private TextField tfStockQuantity;
    @FXML private TableView<Product> txView;
    @FXML private TextField tfSearch;
    @FXML private Button btnRemove;
    private ObservableList<Product> products;
    private ProductRepository productRepository;

    /**
     * Injects the datasource and preloads existing products.
     */
    public void initDataSource(HikariDataSource hikariDataSource) {
        this.productRepository = new ProductRepository(hikariDataSource);
        Iterable<Product> savedProducts = productRepository.findAll();
        products.addAll(StreamSupport.stream(savedProducts.spliterator(), false).collect(Collectors.toList()));
    }

    /**
     * Called by JavaFX to initialize UI controls and bindings.
     */
    public void initialize() {
        products = FXCollections.observableArrayList();
        FilteredList<Product> filteredData = new FilteredList<>(products, product -> true);
        SortedList<Product> sortedList = new SortedList<>(filteredData.sorted(Comparator.comparing(Product::getName)));
        sortedList.comparatorProperty().bind(txView.comparatorProperty());

        List<String> productCategories = List.of("Electronics", "Clothing", "Books", "Home & Garden", "Sports", "Automotive");
        cbCategory.getItems().removeAll();
        cbCategory.getItems().addAll(productCategories);
        cbCategory.getSelectionModel().select("Electronics");

        TableColumn<Product, String> name = new TableColumn<>("Name");
        TableColumn<Product, String> description = new TableColumn<>("Description");
        TableColumn<Product, BigDecimal> price = new TableColumn<>("Price");
        TableColumn<Product, String> category = new TableColumn<>("Category");
        TableColumn<Product, Integer> stockQuantity = new TableColumn<>("Stock Quantity");

        name.setPrefWidth(150);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(event -> {
            Product selectedProduct = event.getRowValue();
            selectedProduct.setName(event.getNewValue());
            productRepository.save(selectedProduct);
        });

        description.setPrefWidth(200);
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        description.setCellFactory(TextFieldTableCell.forTableColumn());
        description.setOnEditCommit(event -> {
            Product selectedProduct = event.getRowValue();
            selectedProduct.setDescription(event.getNewValue());
            productRepository.save(selectedProduct);
        });

        price.setPrefWidth(100);
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        price.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        price.setOnEditCommit(event -> {
            Product selectedProduct = event.getRowValue();
            selectedProduct.setPrice(event.getNewValue());
            productRepository.save(selectedProduct);
        });

        category.setPrefWidth(150);
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        category.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(productCategories)));
        category.setOnEditCommit(event -> {
            Product selectedProduct = event.getRowValue();
            selectedProduct.setCategory(event.getNewValue());
            productRepository.save(selectedProduct);
        });

        stockQuantity.setPrefWidth(120);
        stockQuantity.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));
        stockQuantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        stockQuantity.setOnEditCommit(event -> {
            Product selectedProduct = event.getRowValue();
            selectedProduct.setStockQuantity(event.getNewValue());
            productRepository.save(selectedProduct);
        });

        txView.getColumns().add(name);
        txView.getColumns().add(description);
        txView.getColumns().add(price);
        txView.getColumns().add(category);
        txView.getColumns().add(stockQuantity);
        txView.setEditable(true);
        txView.setTableMenuButtonVisible(true);
        txView.setItems(sortedList);
        // Disable Remove button when no selection
        if (btnRemove != null) {
            btnRemove.disableProperty().bind(txView.getSelectionModel().selectedItemProperty().isNull());
        }
        tfSearch.textProperty().addListener(obs -> {
            String filter = tfSearch.getText();
            if (filter == null || filter.length() == 0) {
                filteredData.setPredicate(product -> true);
            } else {
                filteredData.setPredicate(product -> product.getName().toLowerCase().contains(filter.toLowerCase()));
            }
        });
    }

    

    /**
     * Menu action: quits the application.
     */
    @FXML
    void onQuitClicked() {
        Platform.exit();
    }

    /**
     * Button action: creates and persists a new product from form fields.
     */
    @FXML
    void onAddClicked() {
        try {
            Product product = new Product(
                tfName.getText(), 
                tfDescription.getText(), 
                new BigDecimal(tfPrice.getText()), 
                cbCategory.getValue(), 
                Integer.parseInt(tfStockQuantity.getText())
            );
            products.add(productRepository.save(product));
        } catch (RuntimeException e) {
            new Alert(Alert.AlertType.ERROR).showAndWait();
        }
    }

    /**
     * Button action: deletes the selected product and removes it from the table.
     */
    @FXML
    void onRemoveClicked() {
        Product product = txView.getSelectionModel().getSelectedItem();
        if (product == null) {
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete '" + product.getName() + "'?",
                ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText("Confirm deletion");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    productRepository.deleteById(product.getId());
                    products.remove(product);
                } catch (RuntimeException e) {
                    new Alert(Alert.AlertType.ERROR).showAndWait();
                }
            }
        });
    }

    /**
     * Menu action: shows a simple About dialog.
     */
    @FXML
    void onAboutClicked() {
        new Alert(Alert.AlertType.INFORMATION, "Product Manager v0.1").showAndWait();
    }
}
