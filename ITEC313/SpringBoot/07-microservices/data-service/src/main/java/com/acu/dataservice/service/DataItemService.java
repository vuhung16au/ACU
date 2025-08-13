package com.acu.dataservice.service;

import com.acu.dataservice.model.DataItem;
import com.acu.dataservice.repository.DataItemRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DataItemService {

    private static final Logger logger = LoggerFactory.getLogger(DataItemService.class);

    @Autowired
    private DataItemRepository dataItemRepository;

    @CircuitBreaker(name = "dataService", fallbackMethod = "getAllItemsFallback")
    public List<DataItem> getAllItems() {
        logger.info("Fetching all data items");
        return dataItemRepository.findAll();
    }

    @CircuitBreaker(name = "dataService", fallbackMethod = "getItemByIdFallback")
    public Optional<DataItem> getItemById(Long id) {
        logger.info("Fetching data item with id: {}", id);
        return dataItemRepository.findById(id);
    }

    @CircuitBreaker(name = "dataService", fallbackMethod = "createItemFallback")
    public DataItem createItem(DataItem dataItem) {
        logger.info("Creating new data item: {}", dataItem.getName());
        return dataItemRepository.save(dataItem);
    }

    @CircuitBreaker(name = "dataService", fallbackMethod = "updateItemFallback")
    public Optional<DataItem> updateItem(Long id, DataItem dataItem) {
        logger.info("Updating data item with id: {}", id);
        return dataItemRepository.findById(id)
                .map(existingItem -> {
                    existingItem.setName(dataItem.getName());
                    existingItem.setDescription(dataItem.getDescription());
                    return dataItemRepository.save(existingItem);
                });
    }

    @CircuitBreaker(name = "dataService", fallbackMethod = "deleteItemFallback")
    public boolean deleteItem(Long id) {
        logger.info("Deleting data item with id: {}", id);
        if (dataItemRepository.existsById(id)) {
            dataItemRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @CircuitBreaker(name = "dataService", fallbackMethod = "searchItemsFallback")
    public List<DataItem> searchItems(String keyword) {
        logger.info("Searching data items with keyword: {}", keyword);
        return dataItemRepository.searchByKeyword(keyword);
    }

    // Fallback methods
    public List<DataItem> getAllItemsFallback(Exception e) {
        logger.error("Fallback: Error fetching all items", e);
        return List.of();
    }

    public Optional<DataItem> getItemByIdFallback(Long id, Exception e) {
        logger.error("Fallback: Error fetching item with id: {}", id, e);
        return Optional.empty();
    }

    public DataItem createItemFallback(DataItem dataItem, Exception e) {
        logger.error("Fallback: Error creating item: {}", dataItem.getName(), e);
        throw new RuntimeException("Service temporarily unavailable", e);
    }

    public Optional<DataItem> updateItemFallback(Long id, DataItem dataItem, Exception e) {
        logger.error("Fallback: Error updating item with id: {}", id, e);
        return Optional.empty();
    }

    public boolean deleteItemFallback(Long id, Exception e) {
        logger.error("Fallback: Error deleting item with id: {}", id, e);
        return false;
    }

    public List<DataItem> searchItemsFallback(String keyword, Exception e) {
        logger.error("Fallback: Error searching items with keyword: {}", keyword, e);
        return List.of();
    }
}
