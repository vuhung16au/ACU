package com.acu.dataservice.controller;

import com.acu.dataservice.model.DataItem;
import com.acu.dataservice.service.DataItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/data")
public class DataItemController {

    @Autowired
    private DataItemService dataItemService;

    @GetMapping
    public ResponseEntity<List<DataItem>> getAllItems() {
        List<DataItem> items = dataItemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataItem> getItemById(@PathVariable Long id) {
        Optional<DataItem> item = dataItemService.getItemById(id);
        return item.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DataItem> createItem(@Valid @RequestBody DataItem dataItem) {
        DataItem createdItem = dataItemService.createItem(dataItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataItem> updateItem(@PathVariable Long id, 
                                             @Valid @RequestBody DataItem dataItem) {
        Optional<DataItem> updatedItem = dataItemService.updateItem(id, dataItem);
        return updatedItem.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        boolean deleted = dataItemService.deleteItem(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<DataItem>> searchItems(@RequestParam String keyword) {
        List<DataItem> items = dataItemService.searchItems(keyword);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Data Service is running");
    }
}
