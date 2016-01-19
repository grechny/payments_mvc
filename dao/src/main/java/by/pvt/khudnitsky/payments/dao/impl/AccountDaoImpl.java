/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.constants.ColumnName;
import by.pvt.khudnitsky.payments.constants.SqlRequest;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import by.pvt.khudnitsky.payments.managers.PoolManager;
import by.pvt.khudnitsky.payments.utils.ClosingUtil;
import by.pvt.khudnitsky.payments.utils.EntityBuilder;

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
    public void add(Account account) throws DaoException {
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.ADD_ACCOUNT_WITH_ID);
            statement.setInt(1, account.getId());
            statement.setDouble(2, account.getAmount());
            statement.setString(3, account.getCurrency());
            statement.setInt(4, account.getStatus());
            statement.executeUpdate();
        }
        catch (SQLException e){
            throw new DaoException("Unable to add the account ", e);
        }
        finally{
            ClosingUtil.close(statement);
        }
    }

    @Override
    public List<Account> getAll() throws DaoException {
        List<Account> list = new ArrayList<>();
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_ALL_ACCOUNTS);
            result = statement.executeQuery();
            while (result.next()) {
                Account account = buildAccount(result);
                list.add(account);
            }
        }
        catch (SQLException e){
            throw new DaoException("Unable to return list of accounts ", e);
        }
        finally{
            ClosingUtil.close(result);
            ClosingUtil.close(statement);
        }
        return list;
    }

    @Override
    public Account getById(int id) throws DaoException{
        Account account = null;
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_ACCOUNT_BY_ID);
            statement.setInt(1, id);
            result = statement.executeQuery();

            while (result.next()) {
                account = buildAccount(result);
            }
        }
        catch(SQLException e){
            throw new DaoException("Unable to return the account ", e);
        }
        finally{
            ClosingUtil.close(result);
            ClosingUtil.close(statement);
        }
        return account;
    }

    public boolean isAccountStatusBlocked(int id) throws DaoException{
        boolean isBlocked = false;
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.CHECK_ACCOUNT_STATUS);
            statement.setDouble(1, id);
            result = statement.executeQuery();
            while (result.next()) {
                if (result.getInt("status") == 1) {
                    isBlocked = true;
                }
            }
        }
        catch(SQLException e){
            throw new DaoException("Unable to check account status ", e);
        }
        finally{
            ClosingUtil.close(result);
            ClosingUtil.close(statement);
        }
        return isBlocked;
    }

    public List<Account> getBlockedAccounts() throws DaoException{
        List<Account> list = new ArrayList<>();
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_BLOCKED_ACCOUNTS);
            result = statement.executeQuery();
            while (result.next()) {
                Account account = buildAccount(result);
                list.add(account);
            }
        }
        catch(SQLException e){
            throw new DaoException("Unable to return list of blocked accounts ", e);
        }
        finally{
            ClosingUtil.close(result);
            ClosingUtil.close(statement);
        }
        return list;
    }

    @Override
    public int getMaxId() throws DaoException {
        int lastId = -1;
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_LAST_ACCOUNT_ID);
            result = statement.executeQuery();
            while (result.next()) {
                lastId = result.getInt(1);
            }
        }
        catch(SQLException e){
            throw new DaoException("Unable to return max id of accounts ", e);
        }
        finally{
            ClosingUtil.close(result);
            ClosingUtil.close(statement);
        }
        return lastId;
    }

    public void updateAmount(double amount, int id) throws DaoException{
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.MAKE_ACCOUNT_OPERATION);
            statement.setDouble(1, amount);
            statement.setInt(2, id);
            statement.executeUpdate();
        }
        catch(SQLException e){
            throw new DaoException("Unable to update amount ", e);
        }
        finally{
            ClosingUtil.close(statement);
        }
    }

    public void updateAccountStatus(int id, int status) throws DaoException{
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.CHANGE_STATUS);
            statement.setInt(1, status);
            statement.setInt(2, id);
            statement.executeUpdate();
        }
        catch(SQLException e){
            throw new DaoException("Unable to update account status ", e);
        }
        finally{
            ClosingUtil.close(statement);
        }
    }

    @Override
    public void delete(int id)throws DaoException{
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.DELETE_ACCOUNT_BY_ID);
            statement.setInt(1, id);
            statement.executeUpdate();
        }
        catch(SQLException e){
            throw new DaoException("Unable to delete the account ", e);
        }
        finally{
            ClosingUtil.close(statement);
        }
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