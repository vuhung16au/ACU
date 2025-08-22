package com.acu.javafx.coins.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.acu.javafx.coins.models.CoinPrice;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import okhttp3.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Controller for the main view of the cryptocurrency price tracker
 * Handles UI interactions and API calls to fetch cryptocurrency prices
 */
public class MainViewController {
    // FXML-injected UI components
    @FXML
    private TableView<CoinPrice> priceTable;
    @FXML
    private TableColumn<CoinPrice, String> symbolColumn;
    @FXML
    private TableColumn<CoinPrice, String> priceColumn;
    @FXML
    private TableColumn<CoinPrice, String> timestampColumn;
    @FXML
    private Button refreshButton;

    // Service objects for HTTP requests and background processing
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    
    // List of cryptocurrency symbols to track
    private final String[] symbols = {
        "BTC",
        "ETH",
        "XRP",
        "SOL",
        "DOGE",
        "BCH",
        "LTC",
        "TRUMP"
    };

    /**
     * Initializes the controller and sets up the UI
     * Called automatically by JavaFX after FXML loading
     */
    public void initialize() {
        setupTable();
        loadPrices();
    }

    /**
     * Configures the table columns to display cryptocurrency data
     */
    private void setupTable() {
        symbolColumn.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        timestampColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
    }

    /**
     * Fetches cryptocurrency prices from the Gemini API
     * Runs in background thread to avoid blocking the UI
     */
    @FXML
    public void loadPrices() {
        Task<List<CoinPrice>> task = new Task<>() {
            @Override
            protected List<CoinPrice> call() throws IOException {
                List<CoinPrice> prices = new ArrayList<>();
                
                // Fetch price for each cryptocurrency symbol
                for (String symbol : symbols) {
                    try {
                        CoinPrice price = fetchPrice(symbol);
                        if (price != null) {
                            prices.add(price);
                        }
                    } catch (Exception e) {
                        System.err.println("Error fetching price for " + symbol + ": " + e.getMessage());
                    }
                }
                
                return prices;
            }
        };
        
        // Update UI when prices are successfully loaded
        task.setOnSucceeded(workerStateEvent -> {
            List<CoinPrice> prices = task.getValue();
            priceTable.getItems().clear();
            priceTable.getItems().addAll(prices);
        });
        
        // Handle errors during price fetching
        task.setOnFailed(workerStateEvent -> {
            System.err.println("Failed to load prices: " + task.getException().getMessage());
        });
        
        executorService.submit(task);
    }

    /**
     * Fetches the current price for a specific cryptocurrency from Gemini API
     * @param symbol the cryptocurrency symbol (e.g., "BTC", "ETH")
     * @return CoinPrice object containing the symbol, price, and timestamp
     * @throws IOException if the API request fails
     */
    private CoinPrice fetchPrice(String symbol) throws IOException {
        // Using Gemini API endpoint for ticker data
        String url = "https://api.gemini.com/v1/pubticker/" + symbol + "usd";
        
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response.code());
            }
            
            // Parse JSON response to extract price data
            String json = response.body().string();
            JsonNode root = mapper.readTree(json);
            
            // Extract price from Gemini API response
            BigDecimal price = new BigDecimal(root.get("last").asText());
            Instant timestamp = Instant.now(); // Current timestamp since API doesn't provide it
            
            return new CoinPrice(symbol, price, timestamp);
        }
    }
}
