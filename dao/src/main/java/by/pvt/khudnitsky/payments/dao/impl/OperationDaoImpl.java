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
import by.pvt.khudnitsky.payments.constants.ColumnNames;
import by.pvt.khudnitsky.payments.constants.SqlRequests;
import by.pvt.khudnitsky.payments.managers.PoolManager;
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
    public void add(Operation entity) throws SQLException {
        Connection connection = PoolManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SqlRequests.CREATE_OPERATION);
        statement.setInt(1, entity.getUserId());
        statement.setInt(2, entity.getAccountId());
        statement.setDouble(3, entity.getAmount());
        statement.setString(4, entity.getDescription());
        statement.executeUpdate();
    }

    @Override
    public List<Operation> getAll() throws SQLException {
        Connection connection = PoolManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SqlRequests.GET_ALL_OPERATIONS);
        ResultSet result = statement.executeQuery();
        List<Operation> list = new ArrayList<>();
        while(result.next()){
            Operation operation = buildOperation(result);
            list.add(operation);
        }
        return list;
    }

    @Override
    public Operation getById(int id) throws SQLException {
        Connection connection = PoolManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SqlRequests.GET_OPERATION_BY_ID);
        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();
        Operation operation = null;
        while(result.next()){
            operation = buildOperation(result);
        }
        return operation;
    }

    @Override
    public int getMaxId() throws SQLException {
        Connection connection = PoolManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SqlRequests.GET_LAST_OPERATION_ID);
        ResultSet result = statement.executeQuery();
        int lastId = -1;
        while(result.next()){
            lastId = result.getInt(1);
        }
        return lastId;
    }

    @Override
    public void delete(int id)throws SQLException{
        Connection connection = PoolManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SqlRequests.DELETE_OPERATION_BY_ID);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    private Operation buildOperation(ResultSet result) throws SQLException{
        int id = result.getInt(ColumnNames.OPERATION_ID);
        int accountId = result.getInt(ColumnNames.ACCOUNT_ID);
        int userId = result.getInt(ColumnNames.USER_ID);
        double amount = result.getDouble(ColumnNames.OPERATION_AMOUNT);
        String description = result.getString(ColumnNames.OPERATION_DESCRIPTION);
        String date = result.getString(ColumnNames.OPERATION_DATE);
        Operation operation = EntityBuilder.buildOperation(id, userId, accountId, amount, description, date);
        return operation;
    }
}

