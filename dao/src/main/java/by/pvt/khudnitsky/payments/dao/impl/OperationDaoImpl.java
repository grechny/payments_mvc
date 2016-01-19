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
import by.pvt.khudnitsky.payments.entities.Operation;
import by.pvt.khudnitsky.payments.constants.ColumnName;
import by.pvt.khudnitsky.payments.constants.SqlRequest;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import by.pvt.khudnitsky.payments.managers.PoolManager;
import by.pvt.khudnitsky.payments.utils.ClosingUtil;
import by.pvt.khudnitsky.payments.utils.EntityBuilder;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class OperationDaoImpl extends AbstractDao<Operation> {
    private static OperationDaoImpl instance;

    private OperationDaoImpl(){}

    public static synchronized OperationDaoImpl getInstance(){
        if(instance == null){
            instance = new OperationDaoImpl();
        }
        return instance;
    }

    @Override
    public void add(Operation entity) throws DaoException {
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.CREATE_OPERATION);
            statement.setInt(1, entity.getUserId());
            statement.setInt(2, entity.getAccountId());
            statement.setDouble(3, entity.getAmount());
            statement.setString(4, entity.getDescription());
            statement.executeUpdate();
        }
        catch (SQLException e){
            throw new DaoException("Unable to add the operation ", e);
        }
        finally{
            ClosingUtil.close(statement);
        }
    }

    @Override
    public List<Operation> getAll() throws DaoException {
        List<Operation> list = new ArrayList<>();
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_ALL_OPERATIONS);
            result = statement.executeQuery();
            while (result.next()) {
                Operation operation = buildOperation(result);
                list.add(operation);
            }
        }
        catch (SQLException e){
            throw new DaoException("Unable to return list of operations ", e);
        }
        finally{
            ClosingUtil.close(result);
            ClosingUtil.close(statement);
        }
        return list;
    }

    @Override
    public Operation getById(int id) throws DaoException {
        Operation operation = null;
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_OPERATION_BY_ID);
            statement.setInt(1, id);
            result = statement.executeQuery();
            while (result.next()) {
                operation = buildOperation(result);
            }
        }
        catch (SQLException e){
            throw new DaoException("Unable to return the operation ", e);
        }
        finally{
            ClosingUtil.close(result);
            ClosingUtil.close(statement);
        }
        return operation;
    }

    @Override
    public int getMaxId() throws DaoException {
        int lastId = -1;
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.GET_LAST_OPERATION_ID);
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

    @Override
    public void delete(int id)throws DaoException{
        try {
            connection = PoolManager.getInstance().getConnection();
            statement = connection.prepareStatement(SqlRequest.DELETE_OPERATION_BY_ID);
            statement.setInt(1, id);
            statement.executeUpdate();
        }
        catch (SQLException e){
            throw new DaoException("Unable to delete the operation ", e);
        }
        finally{
            ClosingUtil.close(statement);
        }
    }

    private Operation buildOperation(ResultSet result) throws SQLException{
        int id = result.getInt(ColumnName.OPERATION_ID);
        int accountId = result.getInt(ColumnName.ACCOUNT_ID);
        int userId = result.getInt(ColumnName.USER_ID);
        double amount = result.getDouble(ColumnName.OPERATION_AMOUNT);
        String description = result.getString(ColumnName.OPERATION_DESCRIPTION);
        String date = result.getString(ColumnName.OPERATION_DATE);
        Operation operation = EntityBuilder.buildOperation(id, userId, accountId, amount, description, date);
        return operation;
    }
}

