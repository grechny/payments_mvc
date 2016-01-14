package by.pvt.khudnitsky.payments.services;

import by.pvt.khudnitsky.payments.dao.constants.AccessLevels;
import by.pvt.khudnitsky.payments.dao.constants.SqlRequests;
import by.pvt.khudnitsky.payments.dao.constants.UserType;
import by.pvt.khudnitsky.payments.dao.implementations.UserDao;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.services.utils.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public enum UserService implements Service <User> {
    INSTANCE;
    private Connection connection;

    /**
     * Calls Dao add() method
     *
     * @param entity - object of derived class Entity
     * @throws SQLException
     */
    @Override
    public void add(User entity) throws SQLException {

    }

    /**
     * Calls Dao getAll() method
     *
     * @return list of objects of derived class Entity
     * @throws SQLException
     */
    @Override
    public List<User> getAll() throws SQLException {
        connection = ConnectionPool.INSTANCE.getConnection();
        List<User> users = UserDao.INSTANCE.getAll(connection);
        ConnectionPool.INSTANCE.releaseConnection(connection);
        return users;
    }

    /**
     * Calls Dao getById() method
     *
     * @param id - id of entity
     * @return object of derived class Entity
     * @throws SQLException
     */
    @Override
    public User getById(int id) throws SQLException {
        return null;
    }

    /**
     * Calls Dao update() method
     *
     * @param entity - object of derived class Entity
     * @throws SQLException
     */
    @Override
    public void update(User entity) throws SQLException {

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

    public boolean checkUserAuthorization(String login, String password) throws SQLException{
        connection = ConnectionPool.INSTANCE.getConnection();
        boolean isAuthorized = UserDao.INSTANCE.isAuthorized(connection, login, password);
        ConnectionPool.INSTANCE.releaseConnection(connection);
        return isAuthorized;
    }

    public User getUserByLogin(String login) throws SQLException{
        connection = ConnectionPool.INSTANCE.getConnection();
        User user = UserDao.INSTANCE.getByLogin(connection, login);
        ConnectionPool.INSTANCE.releaseConnection(connection);
        return user;
    }

    public UserType checkAccessLevel(User user) throws SQLException{
        UserType userType = null;
        if(AccessLevels.CLIENT == user.getAccessLevel()){
            userType = UserType.CLIENT;
        }
        else{
            userType = UserType.ADMINISTRATOR;
        }
        return userType;
    }
}
