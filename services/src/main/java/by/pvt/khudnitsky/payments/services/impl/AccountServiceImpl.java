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
    private Connection connection;

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
        AccountDaoImpl.getInstance().add(connection, entity);
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
        return null;
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
        connection = PoolManager.getInstance().getConnection();
        Account account = AccountDaoImpl.getInstance().getById(connection, id);
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

    }

    /**
     * Calls AccountDaoImpl delete() method
     *
     * @param id - Account id
     * @throws SQLException
     */
    @Override
    public void delete(int id) throws SQLException {

    }

    public List<Account> getBlockedAccounts() throws SQLException{
        connection = PoolManager.getInstance().getConnection();
        List<Account> accounts= AccountDaoImpl.getInstance().getBlockedAccounts(connection);
        PoolManager.getInstance().releaseConnection(connection);
        return accounts;
    }

    // TODO Объединить в транзакцию
    public void updateAccountStatus(int id, int status) throws SQLException{
        connection = PoolManager.getInstance().getConnection();
        AccountDaoImpl.getInstance().updateAccountStatus(connection, id, status);
        PoolManager.getInstance().releaseConnection(connection);
    }

    public boolean checkAccountStatus(int id) throws SQLException{
        connection = PoolManager.getInstance().getConnection();
        boolean isBlocked = AccountDaoImpl.getInstance().isAccountStatusBlocked(connection, id);
        PoolManager.getInstance().releaseConnection(connection);
        return isBlocked;
    }

    public void addFunds(User user, String description, double amount) throws SQLException{
        connection = PoolManager.getInstance().getConnection();
        Operation operation = new Operation();
        operation.setUserId(user.getId());
        operation.setAccountId(user.getAccountId());
        operation.setAmount(amount);
        operation.setDescription(description);
        OperationServiceImpl.getInstance().add(operation);
        AccountDaoImpl.getInstance().updateAmount(connection, amount, user.getAccountId());
        PoolManager.getInstance().releaseConnection(connection);
    }


    // TODO истправить создание двух connection
    public void blockAccount(User user, String description) throws SQLException{
        connection = PoolManager.getInstance().getConnection();
        Operation operation = new Operation();
        operation.setUserId(user.getId());
        operation.setAccountId(user.getAccountId());
        operation.setDescription(description);
        OperationDaoImpl.getInstance().add(connection, operation);
        updateAccountStatus(user.getId(), AccountStatus.BLOCKED);
        PoolManager.getInstance().releaseConnection(connection);
    }

    public void payment(User user, String description, double amount) throws SQLException{
        Operation operation = new Operation();
        operation.setUserId(user.getId());
        operation.setAccountId(user.getAccountId());
        operation.setAmount(amount);
        operation.setDescription(description);
        OperationDaoImpl.getInstance().add(connection, operation);
        AccountDaoImpl.getInstance().updateAmount(connection, (-1) * amount, user.getAccountId());
    }
}
