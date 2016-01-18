package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.constants.AccessLevel;
import by.pvt.khudnitsky.payments.constants.UserType;
import by.pvt.khudnitsky.payments.dao.impl.AccountDaoImpl;
import by.pvt.khudnitsky.payments.dao.impl.UserDaoImpl;
import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.services.AbstractService;
import by.pvt.khudnitsky.payments.managers.PoolManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class UserServiceImpl extends AbstractService<User> {
    private static UserServiceImpl instance;

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
        throw new UnsupportedOperationException();
    }

    /**
     * Calls Dao getAll() method
     *
     * @return list of objects of derived class Entity
     * @throws SQLException
     */
    @Override
    public List<User> getAll() throws SQLException {
        Connection connection = PoolManager.getInstance().getConnection();
        connection.setAutoCommit(false);
        List<User> users = null;
        try {
            users = UserDaoImpl.getInstance().getAll();
            connection.commit();
        }
        catch(SQLException e){
            connection.rollback();
        }
        //PoolManager.getInstance().releaseConnection(connection);
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
        throw new UnsupportedOperationException();
    }

    /**
     * Calls Dao update() method
     *
     * @param entity - object of derived class Entity
     * @throws SQLException
     */
    @Override
    public void update(User entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    /**
     * Calls Dao delete() method
     *
     * @param id - id of entity
     * @throws SQLException
     */
    @Override
    public void delete(int id) throws SQLException {
        throw new UnsupportedOperationException();
    }

    public boolean checkUserAuthorization(String login, String password) throws SQLException{
        Connection connection = PoolManager.getInstance().getConnection();
        connection.setAutoCommit(false);
        boolean isAuthorized = false;
        try {
            isAuthorized = UserDaoImpl.getInstance().isAuthorized(login, password);
            connection.commit();
        }
        catch(SQLException e){
            connection.rollback();
        }
        //PoolManager.getInstance().releaseConnection(connection);
        return isAuthorized;
    }

    public User getUserByLogin(String login) throws SQLException{
        Connection connection = PoolManager.getInstance().getConnection();
        connection.setAutoCommit(false);
        User user = null;
        try {
            user = UserDaoImpl.getInstance().getByLogin(login);
            connection.commit();
        }
        catch(SQLException e){
            connection.rollback();
        }
        //PoolManager.getInstance().releaseConnection(connection);
        return user;
    }

    public UserType checkAccessLevel(User user) throws SQLException{
        UserType userType;
        if(AccessLevel.CLIENT == user.getAccessLevel()){
            userType = UserType.CLIENT;
        }
        else{
            userType = UserType.ADMINISTRATOR;
        }
        return userType;
    }

    public boolean checkIsNewUser(User user) throws SQLException{
        Connection connection = PoolManager.getInstance().getConnection();
        connection.setAutoCommit(false);
        boolean isNew = false;
        try {
            if((AccountDaoImpl.getInstance().getById(user.getAccountId()) == null) & (UserDaoImpl.getInstance().isNewUser(user.getLogin()))){
                isNew = true;
            }
            connection.commit();
        }
        catch(SQLException e){
            connection.rollback();
        }
        //PoolManager.getInstance().releaseConnection(connection);
        return isNew;
    }

    public void registrateUser(User user, Account account) throws SQLException{
        Connection connection = PoolManager.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            AccountDaoImpl.getInstance().add(account);
            UserDaoImpl.getInstance().add(user);
            connection.commit();
        }
        catch(SQLException e){
            connection.rollback();
        }
        //PoolManager.getInstance().releaseConnection(connection);
    }
}
