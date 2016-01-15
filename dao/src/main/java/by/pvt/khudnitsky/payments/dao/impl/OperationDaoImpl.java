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
    public List<Operation> getAll() throws SQLException {
        Connection connection = PoolManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SqlRequests.GET_ALL_OPERATIONS);
        ResultSet result = statement.executeQuery();
        List<Operation> list = new ArrayList<>();
        while(result.next()){
            Operation operation = new Operation();
            operation.setId(result.getInt(ColumnNames.OPERATION_ID));
            operation.setUserId(result.getInt(ColumnNames.USER_ID));
            operation.setAccountId(result.getInt(ColumnNames.ACCOUNT_ID));
            operation.setDescription(result.getString(ColumnNames.OPERATION_DESCRIPTION));
            operation.setAmount(result.getDouble(ColumnNames.OPERATION_AMOUNT));
            operation.setDate(result.getString(ColumnNames.OPERATION_DATE));
            list.add(operation);
        }
        return list;
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
    public Operation getById(int id) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(int id) throws SQLException {
        throw new UnsupportedOperationException();
    }
}

