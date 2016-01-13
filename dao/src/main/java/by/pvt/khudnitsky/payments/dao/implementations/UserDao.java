/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.pvt.khudnitsky.payments.dao.constants.UserType;
import by.pvt.khudnitsky.payments.dao.interfaces.AbstractDao;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.dao.constants.AccessLevels;
import by.pvt.khudnitsky.payments.dao.constants.ColumnNames;
import by.pvt.khudnitsky.payments.dao.constants.SqlRequests;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public enum UserDao implements AbstractDao<User> {
    INSTANCE;

    @Override
    public List<User> getAll(Connection connection) throws SQLException {
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

    public User getUserByLogin(Connection connection, String login) throws SQLException{
        User user = null;
        PreparedStatement statement = connection.prepareStatement(SqlRequests.GET_USER_BY_LOGIN);
        statement.setString(1, login);
        ResultSet result = statement.executeQuery();
        while(result.next()){
            user = new User();
            user.setId(result.getInt(ColumnNames.USER_ID));
            user.setFirstName(result.getString(ColumnNames.USER_FIRST_NAME));
            user.setLastName(result.getString(ColumnNames.USER_LAST_NAME));
            user.setLogin(result.getString(ColumnNames.USER_LOGIN));
            user.setPassword(result.getString(ColumnNames.USER_PASSWORD));
            user.setAccountId(result.getInt(ColumnNames.ACCOUNT_ID));
            user.setAccessLevel(result.getInt(ColumnNames.USER_ACCESS_LEVEL));
        }
        return user;
    }

    public boolean isNewUser(Connection connection, String login) throws SQLException{
        boolean isNew = true;
        PreparedStatement statement = connection.prepareStatement(SqlRequests.CHECK_LOGIN);
        statement.setString(1, login);
        ResultSet result = statement.executeQuery();
        if(result.next()){
            isNew = false;
        }
        return isNew;
    }

    public boolean isAuthorized(Connection connection, String login, String password) throws SQLException{
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

    public UserType checkAccessLevel(Connection connection, String login) throws SQLException{
        UserType userType = null;
        PreparedStatement statement = connection.prepareStatement(SqlRequests.CHECK_ACCESS_LEVEL);
        statement.setString(1, login);
        ResultSet result = statement.executeQuery();
        while(result.next()){
            if(AccessLevels.CLIENT == result.getInt("access_level")){
                userType = UserType.CLIENT;
            }
            else{
                userType = UserType.ADMINISTRATOR;
            }
        }
        return userType;
    }

    @Override
    public void create(Connection connection, User user) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(SqlRequests.ADD_USER);
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setInt(3, user.getAccountId());
        statement.setString(4, user.getLogin());
        statement.setString(5, user.getPassword());
        statement.executeUpdate();
    }

    @Override
    public User getById(Connection connection, int id) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Connection connection, int id) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
