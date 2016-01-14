/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.dao.implementations;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.dao.constants.ColumnNames;
import by.pvt.khudnitsky.payments.dao.constants.SqlRequests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public enum AccountDao implements AbstractDao<Account> {
    INSTANCE;

    @Override
    public void add(Connection connection, Account account) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SqlRequests.ADD_ACCOUNT_WITH_ID);
        statement.setInt(1, account.getId());
        statement.setDouble(2, account.getAmount());
        statement.setString(3, account.getCurrency());
        statement.setInt(4, account.getStatus());
        statement.executeUpdate();
    }

    @Override
    public List<Account> getAll(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SqlRequests.GET_ALL_ACCOUNTS);
        ResultSet result = statement.executeQuery();
        List<Account> list = new ArrayList<>();

        while(result.next()){
            Account account = new Account();
            account.setAmount(result.getDouble(ColumnNames.ACCOUNT_AMOUNT));
            account.setId(result.getInt(ColumnNames.ACCOUNT_ID));
            account.setCurrency(result.getString(ColumnNames.ACCOUNT_CURRENCY));
            account.setStatus(result.getInt(ColumnNames.ACCOUNT_STATUS));
            list.add(account);
        }
        return list;
    }

    @Override
    public Account getById(Connection connection, int id) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(SqlRequests.GET_ACCOUNT_BY_ID);
        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();
        Account account = null;
        while(result.next()){
            account = new Account();
            account.setAmount(result.getDouble(ColumnNames.ACCOUNT_AMOUNT));
            account.setId(result.getInt(ColumnNames.ACCOUNT_ID));
            account.setCurrency(result.getString(ColumnNames.ACCOUNT_CURRENCY));
            account.setStatus(result.getInt(ColumnNames.ACCOUNT_STATUS));
        }
        return account;
    }

    public boolean isAccountStatusBlocked(Connection connection, int id) throws SQLException{
        boolean isBlocked = false;
        PreparedStatement statement = connection.prepareStatement(SqlRequests.CHECK_ACCOUNT_STATUS);
        statement.setDouble(1, id);
        ResultSet result = statement.executeQuery();
        while(result.next()){
            if(result.getInt("status") == 1){
                isBlocked = true;
            }
        }
        return isBlocked;
    }

    public List<Account> getBlockedAccounts(Connection connection) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(SqlRequests.GET_BLOCKED_ACCOUNTS);
        ResultSet result = statement.executeQuery();
        List<Account> list = new ArrayList<>();
        while(result.next()){
            Account account = new Account();
            account.setId(result.getInt(ColumnNames.ACCOUNT_ID));
            account.setAmount(result.getDouble(ColumnNames.ACCOUNT_AMOUNT));
            account.setCurrency(result.getString(ColumnNames.ACCOUNT_CURRENCY));
            account.setStatus(result.getInt(ColumnNames.ACCOUNT_STATUS));
            list.add(account);
        }
        return list;
    }
//
//    @Override
//    public void update(Connection connection, Account account) throws SQLException{
//        PreparedStatement statement = connection.prepareStatement(SqlRequests.MAKE_ACCOUNT_OPERATION);
//        statement.setDouble(1, amount);
//        statement.setInt(2, id);
//        statement.executeUpdate();
//    }

    public void updateAmount(Connection connection, double amount, int id) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(SqlRequests.MAKE_ACCOUNT_OPERATION);
        statement.setDouble(1, amount);
        statement.setInt(2, id);
        statement.executeUpdate();
    }

    public void updateAccountStatus(Connection connection, int id, int status) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(SqlRequests.CHANGE_STATUS);
        statement.setInt(1, status);
        statement.setInt(2, id);
        statement.executeUpdate();
    }

    @Override
    public void delete(Connection connection, int id)throws SQLException{
        PreparedStatement statement = connection.prepareStatement(SqlRequests.DELETE_ACCOUNT_BY_ID);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
}