/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.constants.ColumnNames;
import by.pvt.khudnitsky.payments.constants.SqlRequests;
import by.pvt.khudnitsky.payments.managers.PoolManager;
import by.pvt.khudnitsky.payments.utils.EntityBuilder;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class UserDaoImpl extends AbstractDao<User> {
    private static UserDaoImpl instance;

    private UserDaoImpl(){}

    public static synchronized UserDaoImpl getInstance(){
        if(instance == null){
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public void add(User user) throws SQLException{
        Connection connection = PoolManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SqlRequests.ADD_USER);
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setInt(3, user.getAccountId());
        statement.setString(4, user.getLogin());
        statement.setString(5, user.getPassword());
        statement.executeUpdate();
    }

    @Override
    public List<User> getAll() throws SQLException {
        Connection connection = PoolManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SqlRequests.GET_ALL_CLIENTS);
        ResultSet result = statement.executeQuery();
        List<User> list = new ArrayList<>();
        while(result.next()){
            User user = new User();
            user.setFirstName(result.getString(ColumnNames.USER_FIRST_NAME));
            user.setLastName(result.getString(ColumnNames.USER_LAST_NAME));
            list.add(user);
        }
        return list;
    }

    @Override
    public User getById(int id) throws SQLException {
        Connection connection = PoolManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SqlRequests.GET_USER_BY_ID);
        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();
        User user = null;
        while(result.next()){
            user = buildUser(result);
        }
        return user;
    }

    public User getByLogin(String login) throws SQLException{
        Connection connection = PoolManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SqlRequests.GET_USER_BY_LOGIN);
        statement.setString(1, login);
        ResultSet result = statement.executeQuery();
        User user = null;
        while(result.next()){
            user = buildUser(result);
        }
        return user;
    }

    public boolean isNewUser(String login) throws SQLException{
        Connection connection = PoolManager.getInstance().getConnection();
        boolean isNew = true;
        PreparedStatement statement = connection.prepareStatement(SqlRequests.CHECK_LOGIN);
        statement.setString(1, login);
        ResultSet result = statement.executeQuery();
        if(result.next()){
            isNew = false;
        }
        return isNew;
    }

    public boolean isAuthorized(String login, String password) throws SQLException{
        Connection connection = PoolManager.getInstance().getConnection();
        boolean isLogIn = false;
        PreparedStatement statement = connection.prepareStatement(SqlRequests.CHECK_AUTHORIZATION);
        statement.setString(1, login);
        statement.setString(2, password);
        ResultSet result = statement.executeQuery();
        if(result.next()){
            isLogIn = true;
        }
        return isLogIn;
    }

    @Override
    public int getMaxId() throws SQLException {
        Connection connection = PoolManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SqlRequests.GET_LAST_USER_ID);
        ResultSet result = statement.executeQuery();
        int lastId = -1;
        while(result.next()){
            lastId = result.getInt(1);
        }
        return lastId;
    }

    @Override
    public void delete(int id) throws SQLException {
        Connection connection = PoolManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SqlRequests.DELETE_USER_BY_ID);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    private User buildUser(ResultSet result) throws SQLException{
        int id = result.getInt(ColumnNames.USER_ID);
        String firstName = result.getString(ColumnNames.USER_FIRST_NAME);
        String lastName = result.getString(ColumnNames.USER_LAST_NAME);
        int accountId = result.getInt(ColumnNames.ACCOUNT_ID);
        String login = result.getString(ColumnNames.USER_LOGIN);
        String password = result.getString(ColumnNames.USER_PASSWORD);
        int accessLevel = result.getInt(ColumnNames.USER_ACCESS_LEVEL);
        User user = EntityBuilder.buildUser(id, firstName, lastName, accountId, login, password, accessLevel);
        return user;
    }
}
