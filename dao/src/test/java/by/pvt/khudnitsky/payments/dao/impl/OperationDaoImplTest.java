package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.entities.Entity;
import by.pvt.khudnitsky.payments.entities.Operation;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.managers.PoolManager;
import org.junit.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class OperationDaoImplTest {

    @Test
    public void testGetInstance() throws Exception {
        OperationDaoImpl instance1 = OperationDaoImpl.getInstance();
        OperationDaoImpl instance2 = OperationDaoImpl.getInstance();
        Assert.assertEquals(instance1.hashCode(), instance2.hashCode());
    }

    @Test
    public void testAdd() throws Exception {
        Account account = EntityBuilder.buildAccount(100, "TEST", 0, 0);
        AccountDaoImpl.getInstance().add(account);
        User user = EntityBuilder.buildUser(100, "TEST", "TEST", account.getId(), "TEST", "TEST", 0);
        UserDaoImpl.getInstance().add(user);
        int userActualId = UserDaoImpl.getInstance().getMaxId();
        Operation expected = EntityBuilder.buildOperation(100, userActualId, user.getAccountId(), 100, "TEST", "01.01.01");
        OperationDaoImpl.getInstance().add(expected);
        int operationActualId = OperationDaoImpl.getInstance().getMaxId();
        Operation actual = OperationDaoImpl.getInstance().getById(operationActualId);
        AccountDaoImpl.getInstance().delete(account.getId());
        UserDaoImpl.getInstance().delete(userActualId);
        OperationDaoImpl.getInstance().delete(operationActualId);
        Assert.assertEquals(expected.getDescription(), actual.getDescription());  // TODO исправить
    }

    @Test
    public void testDelete() throws Exception{
        Account account = EntityBuilder.buildAccount(100, "TEST", 0, 0);
        AccountDaoImpl.getInstance().add(account);
        User user = EntityBuilder.buildUser(100, "TEST", "TEST", account.getId(), "TEST", "TEST", 0);
        UserDaoImpl.getInstance().add(user);
        int userActualId = UserDaoImpl.getInstance().getMaxId();
        Operation operation = EntityBuilder.buildOperation(100, userActualId, user.getAccountId(), 100, "TEST", "01.01.01");
        OperationDaoImpl.getInstance().add(operation);
        int operationActualId = OperationDaoImpl.getInstance().getMaxId();
        OperationDaoImpl.getInstance().delete(operationActualId);
        Operation actual = OperationDaoImpl.getInstance().getById(operationActualId);
        AccountDaoImpl.getInstance().delete(account.getId());
        Assert.assertNull(actual);
    }
}