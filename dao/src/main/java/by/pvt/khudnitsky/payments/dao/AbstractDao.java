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
public interface AbstractDao  <T extends Entity> {
    void add(Connection connection, T entity) throws SQLException;
    List<T> getAll(Connection connection) throws SQLException;
    T getById(Connection connection, int id) throws SQLException;
    //void update(Connection connection, T entity) throws SQLException;
    void delete(Connection connection, int id) throws SQLException;
}
