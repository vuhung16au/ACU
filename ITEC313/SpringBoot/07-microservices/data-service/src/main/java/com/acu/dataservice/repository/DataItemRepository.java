package com.acu.dataservice.repository;

import com.acu.dataservice.model.DataItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataItemRepository extends JpaRepository<DataItem, Long> {
    
    List<DataItem> findByNameContainingIgnoreCase(String name);
    
    List<DataItem> findByDescriptionContainingIgnoreCase(String description);
    
    @Query("SELECT d FROM DataItem d WHERE d.name LIKE %:keyword% OR d.description LIKE %:keyword%")
    List<DataItem> searchByKeyword(@Param("keyword") String keyword);
    
    @Query("SELECT COUNT(d) FROM DataItem d")
    long countAllItems();
}
