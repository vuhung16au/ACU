package com.acu.javafx.products.persistence.repository;

import java.util.Optional;

/**
 * Minimal CRUD repository abstraction.
 */
public interface Repository<T, ID> {
    /** Finds an entity by id. */
    Optional<T> findById(ID id);
    /** Returns all entities. */
    Iterable<T> findAll();
    /** Saves the entity (insert or update). */
    T save(T entity);
    /** Deletes an entity by id. */
    void deleteById(ID id);
}
