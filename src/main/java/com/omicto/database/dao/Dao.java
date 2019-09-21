package com.omicto.database.dao;

import java.sql.SQLException;
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
     */
    void save(T t) throws SQLException;

    /**
     * Persists a list of objects
     * @param t the list of objects
     */
    void saveAll(List<T> t) throws SQLException;

    /**
     * Updates an object identified by id
     * @param id the id
     * @param t the updated object
     */
    void update(K id, T t) throws SQLException;

    /**
     * Returns all the stored objects
     * @return all the stored objects
     */
    List<T> getAll() throws SQLException;

    /**
     * Retrieves an object identified by its Id, null otherwise
     * @param id the id
     * @return null if the item is not found, an object identified by ID otherwise
     */
    T getOne(K id) throws SQLException;

    /**
     * Deletes an object identified by its Id
     * @param id the id
     * @return the object that was deleted, null if it doesnt exist
     */
    T deleteById(K id);
}
