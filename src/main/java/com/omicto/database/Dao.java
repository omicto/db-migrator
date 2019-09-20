package com.omicto.database;

import java.util.List;

/**
 * Simple Data Access Object interface
 * @param <K> The ID type
 * @param <T> The type this DAO persists
 */
public interface Dao<K, T> {
    /**
     * Persists an object
     * @param t the object
     * @return the object that was persisted (it may have suffered changes)
     */
    T save(T t);

    /**
     * Updates an object identified by id
     * @param id the id
     * @param t the updated object
     * @return the updated object
     */
    T update(K id, T t);

    /**
     * Returns all the stored objects
     * @return all the stored objects
     */
    List<T> getAll();

    /**
     * Retrieves an object identified by its Id, null otherwise
     * @param id the id
     * @return null if the item is not found, an object identified by ID otherwise
     */
    T getOne(K id);

    /**
     * Deletes an object identified by its Id
     * @param id the id
     * @return the object that was deleted, null if it doesnt exist
     */
    T deleteById(K id);
}
