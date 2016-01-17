/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.dao.IDao;
import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.constants.ColumnNames;
import by.pvt.khudnitsky.payments.constants.SqlRequests;
import by.pvt.khudnitsky.payments.managers.PoolManager;

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
public class AccountDaoImpl extends AbstractDao<Account> {
    private static AccountDaoImpl instance;

    private AccountDaoImpl(){}

    public static synchronized AccountDaoImpl getInstance(){
        if(instance == null){
            instance = new AccountDaoImpl();
        }
        return instance;
    }

    @Override
    public void add(Account account) throws SQLException {
        Connection connection = PoolManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SqlRequests.ADD_ACCOUNT_WITH_ID);
        statement.setInt(1, account.getId());
        statement.setDouble(2, account.getAmount());
        statement.setString(3, account.getCurrency());
        statement.setInt(4, account.getStatus());
        statement.executeUpdate();
    }

    @Override
    public List<Account> getAll() throws SQLException {
        Connection connection = PoolManager.getInstance().getConnection();
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
    public Account getById(int id) throws SQLException{
        Connection connection = PoolManager.getInstance().getConnection();
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

    public boolean isAccountStatusBlocked(int id) throws SQLException{
        Connection connection = PoolManager.getInstance().getConnection();
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

    public List<Account> getBlockedAccounts() throws SQLException{
        Connection connection = PoolManager.getInstance().getConnection();
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

    public void updateAmount(double amount, int id) throws SQLException{
        Connection connection = PoolManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SqlRequests.MAKE_ACCOUNT_OPERATION);
        statement.setDouble(1, amount);
        statement.setInt(2, id);
        statement.executeUpdate();
    }

    public void updateAccountStatus(int id, int status) throws SQLException{
        Connection connection = PoolManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SqlRequests.CHANGE_STATUS);
        statement.setInt(1, status);
        statement.setInt(2, id);
        statement.executeUpdate();
    }

    @Override
    public void delete(int id)throws SQLException{
        Connection connection = PoolManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SqlRequests.DELETE_ACCOUNT_BY_ID);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
}