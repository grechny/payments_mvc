/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.dao;

import by.pvt.khudnitsky.payments.entities.Entity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Describes the interface <b>Entity</b>
 * @author khudnitsky
 * @version 1.0
 *
 */
public interface IDao<T extends Entity> {
    void add(T entity) throws SQLException;
    List<T> getAll() throws SQLException;
    T getById(int id) throws SQLException;
    void delete(int id) throws SQLException;

    //void update(T entity) throws SQLException;
}
