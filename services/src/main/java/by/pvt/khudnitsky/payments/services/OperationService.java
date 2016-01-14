package by.pvt.khudnitsky.payments.services;

import by.pvt.khudnitsky.payments.dao.implementations.AccountDao;
import by.pvt.khudnitsky.payments.dao.implementations.OperationDao;
import by.pvt.khudnitsky.payments.entities.Operation;
import by.pvt.khudnitsky.payments.services.utils.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public enum OperationService implements Service <Operation> {
    INSTANCE;

    private Connection connection;
    /**
     * Calls Dao add() method
     *
     * @param entity - object of derived class Entity
     * @throws SQLException
     */
    @Override
    public void add(Operation entity) throws SQLException {
        connection = ConnectionPool.INSTANCE.getConnection();
        OperationDao.INSTANCE.add(connection, entity);
        ConnectionPool.INSTANCE.releaseConnection(connection);
    }

    /**
     * Calls Dao getAll() method
     *
     * @return list of objects of derived class Entity
     * @throws SQLException
     */
    @Override
    public List<Operation> getAll() throws SQLException {
        connection = ConnectionPool.INSTANCE.getConnection();
        List<Operation> operations = OperationDao.INSTANCE.getAll(connection);
        ConnectionPool.INSTANCE.releaseConnection(connection);
        return operations;
    }

    /**
     * Calls Dao getById() method
     *
     * @param id - id of entity
     * @return object of derived class Entity
     * @throws SQLException
     */
    @Override
    public Operation getById(int id) throws SQLException {
        return null;
    }

    /**
     * Calls Dao update() method
     *
     * @param entity - object of derived class Entity
     * @throws SQLException
     */
    @Override
    public void update(Operation entity) throws SQLException {

    }

    /**
     * Calls Dao delete() method
     *
     * @param id - id of entity
     * @throws SQLException
     */
    @Override
    public void delete(int id) throws SQLException {

    }
}
