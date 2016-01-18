package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.entities.Operation;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.utils.EntityBuilder;
import org.junit.*;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class OperationDaoImplTest {
    private Account account;
    private User user;
    private Operation expected;

    @Before
    public void setUp(){
        account = EntityBuilder.buildAccount(100, "TEST", 0, 0);
        user = EntityBuilder.buildUser(100, "TEST", "TEST", account.getId(), "TEST", "TEST", 0);
        expected = EntityBuilder.buildOperation(100, user.getId(), user.getAccountId(), 100, "TEST", "01.01.01");
    }

    @After
    public void tearDown(){
        account = null;
        user = null;
        expected = null;
    }
    @Test
    public void testGetInstance() throws Exception {
        OperationDaoImpl instance1 = OperationDaoImpl.getInstance();
        OperationDaoImpl instance2 = OperationDaoImpl.getInstance();
        Assert.assertEquals(instance1.hashCode(), instance2.hashCode());
    }

    @Test
    public void testAdd() throws Exception {
        AccountDaoImpl.getInstance().add(account);
        UserDaoImpl.getInstance().add(user);
        int userActualId = UserDaoImpl.getInstance().getMaxId();
        expected.setUserId(userActualId);
        OperationDaoImpl.getInstance().add(expected);
        int operationActualId = OperationDaoImpl.getInstance().getMaxId();
        Operation actual = OperationDaoImpl.getInstance().getById(operationActualId);
        AccountDaoImpl.getInstance().delete(account.getId());
        OperationDaoImpl.getInstance().delete(operationActualId);
        Assert.assertEquals(expected.getDescription(), actual.getDescription());  // TODO исправить
    }

    @Test
    public void testDelete() throws Exception{
        AccountDaoImpl.getInstance().add(account);
        UserDaoImpl.getInstance().add(user);
        int userActualId = UserDaoImpl.getInstance().getMaxId();
        expected.setUserId(userActualId);
        OperationDaoImpl.getInstance().add(expected);
        int operationActualId = OperationDaoImpl.getInstance().getMaxId();
        OperationDaoImpl.getInstance().delete(operationActualId);
        Operation actual = OperationDaoImpl.getInstance().getById(operationActualId);
        AccountDaoImpl.getInstance().delete(account.getId());
        Assert.assertNull(actual);
    }
}