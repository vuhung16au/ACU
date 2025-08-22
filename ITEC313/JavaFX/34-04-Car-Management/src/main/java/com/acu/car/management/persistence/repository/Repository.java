package com.acu.car.management.persistence.repository;

import java.util.Optional;

/**
 * Generic repository interface defining basic CRUD operations
 * @param <T> The entity type
 * @param <ID> The entity identifier type
 */
public interface Repository<T, ID> {
    /**
     * Find an entity by its ID
     */
    Optional<T> findById(ID id);
    
    /**
     * Find all entities
     */
    Iterable<T> findAll();
    
    /**
     * Save an entity (create or update)
     */
    T save(T entity);
    
    /**
     * Delete an entity by its ID
     */
    void deleteById(ID id);
    
    /**
     * Delete all entities
     */
    void deleteAll();
}
