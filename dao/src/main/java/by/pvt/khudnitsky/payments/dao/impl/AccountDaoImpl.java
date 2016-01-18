/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.constants.ColumnName;
import by.pvt.khudnitsky.payments.constants.SqlRequest;
import by.pvt.khudnitsky.payments.managers.PoolManager;
import by.pvt.khudnitsky.payments.utils.EntityBuilder;

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
        PreparedStatement statement = connection.prepareStatement(SqlRequest.ADD_ACCOUNT_WITH_ID);
        statement.setInt(1, account.getId());
        statement.setDouble(2, account.getAmount());
        statement.setString(3, account.getCurrency());
        statement.setInt(4, account.getStatus());
        statement.executeUpdate();
    }

    @Override
    public List<Account> getAll() throws SQLException {
        Connection connection = PoolManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SqlRequest.GET_ALL_ACCOUNTS);
        ResultSet result = statement.executeQuery();
        List<Account> list = new ArrayList<>();
        while(result.next()){
            Account account = buildAccount(result);
            list.add(account);
        }
        return list;
    }

    @Override
    public Account getById(int id) throws SQLException{
        Connection connection = PoolManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SqlRequest.GET_ACCOUNT_BY_ID);
        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();
        Account account = null;
        while(result.next()){
            account = buildAccount(result);
        }
        return account;
    }

    public boolean isAccountStatusBlocked(int id) throws SQLException{
        Connection connection = PoolManager.getInstance().getConnection();
        boolean isBlocked = false;
        PreparedStatement statement = connection.prepareStatement(SqlRequest.CHECK_ACCOUNT_STATUS);
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
        PreparedStatement statement = connection.prepareStatement(SqlRequest.GET_BLOCKED_ACCOUNTS);
        ResultSet result = statement.executeQuery();
        List<Account> list = new ArrayList<>();
        while(result.next()){
            Account account = buildAccount(result);
            list.add(account);
        }
        return list;
    }

    @Override
    public int getMaxId() throws SQLException {
        Connection connection = PoolManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SqlRequest.GET_LAST_ACCOUNT_ID);
        ResultSet result = statement.executeQuery();
        int lastId = -1;
        while(result.next()){
            lastId = result.getInt(1);
        }
        return lastId;
    }

    public void updateAmount(double amount, int id) throws SQLException{
        Connection connection = PoolManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SqlRequest.MAKE_ACCOUNT_OPERATION);
        statement.setDouble(1, amount);
        statement.setInt(2, id);
        statement.executeUpdate();
    }

    public void updateAccountStatus(int id, int status) throws SQLException{
        Connection connection = PoolManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SqlRequest.CHANGE_STATUS);
        statement.setInt(1, status);
        statement.setInt(2, id);
        statement.executeUpdate();
    }

    @Override
    public void delete(int id)throws SQLException{
        Connection connection = PoolManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SqlRequest.DELETE_ACCOUNT_BY_ID);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    private Account buildAccount(ResultSet result) throws SQLException{
        int id = result.getInt(ColumnName.ACCOUNT_ID);
        String currency = result.getString(ColumnName.ACCOUNT_CURRENCY);
        double amount = result.getDouble(ColumnName.ACCOUNT_AMOUNT);
        int status = result.getInt(ColumnName.ACCOUNT_STATUS);
        Account account = EntityBuilder.buildAccount(id, currency, amount, status);
        return account;
    }
}