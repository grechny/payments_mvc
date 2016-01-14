package by.pvt.khudnitsky.payments.services;

import by.pvt.khudnitsky.payments.dao.implementations.AccountDao;
import by.pvt.khudnitsky.payments.dao.implementations.OperationDao;
import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.entities.Operation;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.services.utils.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public enum AccountService implements Service <Account>{
    INSTANCE;

    private Connection connection;

    /**
     * Calls AccountDao add() method
     *
     * @param entity - Account object
     * @throws SQLException
     */
    @Override
    public void add(Account entity) throws SQLException {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        AccountDao.INSTANCE.add(connection, entity);
        ConnectionPool.INSTANCE.releaseConnection(connection);
    }

    /**
     * Calls AccountDao getAll() method
     *
     * @return list of Account objects
     * @throws SQLException
     */
    @Override
    public List<Account> getAll() throws SQLException {
        return null;
    }

    /**
     * Calls AccountDao getById() method
     *
     * @param id - Account id
     * @return Account object
     * @throws SQLException
     */
    @Override
    public Account getById(int id) throws SQLException {
        return null;
    }

    /**
     * Calls AccountDao update() method
     *
     * @param entity - Account object
     * @throws SQLException
     */
    @Override
    public void update(Account entity) throws SQLException {

    }

    /**
     * Calls AccountDao delete() method
     *
     * @param id - Account id
     * @throws SQLException
     */
    @Override
    public void delete(int id) throws SQLException {

    }

    public List<Account> getBlockedAccounts() throws SQLException{
        connection = ConnectionPool.INSTANCE.getConnection();
        List<Account> accounts= AccountDao.INSTANCE.getBlockedAccounts(connection);
        ConnectionPool.INSTANCE.releaseConnection(connection);
        return accounts;
    }

    // TODO Объединить в транзакцию
    public void updateAccountStatus(int id, int status) throws SQLException{
        connection = ConnectionPool.INSTANCE.getConnection();
        AccountDao.INSTANCE.updateAccountStatus(connection, id, status);
        ConnectionPool.INSTANCE.releaseConnection(connection);
    }

    public boolean checkAccountStatus(int id) throws SQLException{
        boolean isBlocked = false;
        connection = ConnectionPool.INSTANCE.getConnection();
        AccountDao.INSTANCE.isAccountStatusBlocked(connection, id);
        ConnectionPool.INSTANCE.releaseConnection(connection);
        return isBlocked;
    }

    public void makeOperation(User user, String description, double amount) throws SQLException{
        connection = ConnectionPool.INSTANCE.getConnection();
        Operation operation = new Operation();
        operation.setUserId(user.getId());
        operation.setAccountId(user.getAccountId());
        operation.setAmount(amount);
        operation.setDescription(description);
        OperationDao.INSTANCE.add(connection, operation);
        AccountDao.INSTANCE.updateAmount(connection, amount, user.getAccountId());
        ConnectionPool.INSTANCE.releaseConnection(connection);
    }
}
