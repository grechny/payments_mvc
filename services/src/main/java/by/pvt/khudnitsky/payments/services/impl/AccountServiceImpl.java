package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.dao.impl.AccountDaoImpl;
import by.pvt.khudnitsky.payments.dao.impl.OperationDaoImpl;
import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.entities.Operation;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.constants.AccountStatus;
import by.pvt.khudnitsky.payments.services.AbsractService;
import by.pvt.khudnitsky.payments.managers.PoolManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class AccountServiceImpl extends AbsractService<Account> {
    private static AccountServiceImpl instance;

    private AccountServiceImpl(){}

    public static synchronized AccountServiceImpl getInstance(){
        if(instance == null){
            instance = new AccountServiceImpl();
        }
        return instance;
    }

    /**
     * Calls AccountDaoImpl add() method
     *
     * @param entity - Account object
     * @throws SQLException
     */
    @Override
    public void add(Account entity) throws SQLException {
        Connection connection = PoolManager.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            AccountDaoImpl.getInstance().add(entity);
            connection.commit();
        }
        catch(SQLException e){
            connection.rollback();
        }
        PoolManager.getInstance().releaseConnection(connection);
    }

    /**
     * Calls AccountDaoImpl getAll() method
     *
     * @return list of Account objects
     * @throws SQLException
     */
    @Override
    public List<Account> getAll() throws SQLException {
        throw new UnsupportedOperationException();
    }

    /**
     * Calls AccountDaoImpl getById() method
     *
     * @param id - Account id
     * @return Account object
     * @throws SQLException
     */
    @Override
    public Account getById(int id) throws SQLException {
        Connection connection = PoolManager.getInstance().getConnection();
        connection.setAutoCommit(false);
        Account account = null;
        try {
             account = AccountDaoImpl.getInstance().getById(id);
            connection.commit();
        }
        catch (SQLException e){
            connection.rollback();
        }
        PoolManager.getInstance().releaseConnection(connection);
        return account;
    }

    /**
     * Calls AccountDaoImpl update() method
     *
     * @param entity - Account object
     * @throws SQLException
     */
    @Override
    public void update(Account entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    /**
     * Calls AccountDaoImpl delete() method
     *
     * @param id - Account id
     * @throws SQLException
     */
    @Override
    public void delete(int id) throws SQLException {
        throw new UnsupportedOperationException();
    }

    public List<Account> getBlockedAccounts() throws SQLException{
        Connection connection = PoolManager.getInstance().getConnection();
        connection.setAutoCommit(false);
        List<Account> accounts = null;
        try {
            accounts = AccountDaoImpl.getInstance().getBlockedAccounts();
            connection.commit();
        }
        catch (SQLException e){
            connection.rollback();
        }
        PoolManager.getInstance().releaseConnection(connection);
        return accounts;
    }


    public void updateAccountStatus(int id, int status) throws SQLException{
        Connection connection = PoolManager.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            AccountDaoImpl.getInstance().updateAccountStatus(id, status);
            connection.commit();
        }
        catch (SQLException e){
            connection.rollback();
        }
        PoolManager.getInstance().releaseConnection(connection);
    }

    public boolean checkAccountStatus(int id) throws SQLException{
        Connection connection = PoolManager.getInstance().getConnection();
        connection.setAutoCommit(false);
        boolean isBlocked = false;
        try {
            isBlocked = AccountDaoImpl.getInstance().isAccountStatusBlocked(id);
            connection.commit();
        }
        catch (SQLException e){
            connection.rollback();
        }
        PoolManager.getInstance().releaseConnection(connection);
        return isBlocked;
    }

    public void addFunds(User user, String description, double amount) throws SQLException{
        Connection connection = PoolManager.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            Operation operation = buildOperation(user, description, amount);
            OperationServiceImpl.getInstance().add(operation);
            AccountDaoImpl.getInstance().updateAmount(amount, user.getAccountId());
            connection.commit();
        }
        catch (SQLException e){
            connection.rollback();
        }
        PoolManager.getInstance().releaseConnection(connection);
    }

    public void blockAccount(User user, String description) throws SQLException{
        Connection connection = PoolManager.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            Operation operation = buildOperation(user, description, 0);
            OperationDaoImpl.getInstance().add(operation);
            AccountDaoImpl.getInstance().updateAccountStatus(user.getId(), AccountStatus.BLOCKED);
            connection.commit();
        }
        catch (SQLException e){
            connection.rollback();
        }
        PoolManager.getInstance().releaseConnection(connection);
    }

    public void payment(User user, String description, double amount) throws SQLException{
        Connection connection = PoolManager.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            Operation operation = buildOperation(user, description, amount);
            OperationDaoImpl.getInstance().add(operation);
            AccountDaoImpl.getInstance().updateAmount((-1) * amount, user.getAccountId());
            connection.commit();
        }
        catch (SQLException e){
            connection.rollback();
        }
        PoolManager.getInstance().releaseConnection(connection);
    }

    private Operation buildOperation(User user, String description, double amount){
        Operation operation = new Operation();
        operation.setUserId(user.getId());
        operation.setAccountId(user.getAccountId());
        operation.setAmount(amount);
        operation.setDescription(description);
        return operation;
    }
}
