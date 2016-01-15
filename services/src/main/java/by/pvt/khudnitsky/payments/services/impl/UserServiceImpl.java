package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.constants.AccessLevels;
import by.pvt.khudnitsky.payments.constants.UserType;
import by.pvt.khudnitsky.payments.dao.impl.UserDaoImpl;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.services.AbsractService;
import by.pvt.khudnitsky.payments.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class UserServiceImpl extends AbsractService<User> {
    private static UserServiceImpl instance;
    private Connection connection;

    private UserServiceImpl(){}

    public static synchronized UserServiceImpl getInstance(){
        if(instance == null){
            instance = new UserServiceImpl();
        }
        return instance;
    }

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
        connection = ConnectionPool.getInstance().getConnection();
        List<User> users = UserDaoImpl.getInstance().getAll(connection);
        ConnectionPool.getInstance().releaseConnection(connection);
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
        connection = ConnectionPool.getInstance().getConnection();
        boolean isAuthorized = UserDaoImpl.getInstance().isAuthorized(connection, login, password);
        ConnectionPool.getInstance().releaseConnection(connection);
        return isAuthorized;
    }

    public User getUserByLogin(String login) throws SQLException{
        connection = ConnectionPool.getInstance().getConnection();
        User user = UserDaoImpl.getInstance().getByLogin(connection, login);
        ConnectionPool.getInstance().releaseConnection(connection);
        return user;
    }

    public UserType checkAccessLevel(User user) throws SQLException{
        UserType userType;
        if(AccessLevels.CLIENT == user.getAccessLevel()){
            userType = UserType.CLIENT;
        }
        else{
            userType = UserType.ADMINISTRATOR;
        }
        return userType;
    }
}
