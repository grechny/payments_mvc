package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.managers.PoolManager;
import org.junit.*;

import java.sql.*;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class AccountDaoImplTest {

    @Test
    public void testGetInstance() throws Exception {
        AccountDaoImpl instance1 = AccountDaoImpl.getInstance();
        AccountDaoImpl instance2 = AccountDaoImpl.getInstance();
        Assert.assertEquals(instance1.hashCode(), instance2.hashCode());
    }

    @Test
    public void testAdd() throws Exception{
        Account expected = EntityBuilder.buildAccount(100, "TEST", 100, 0);
        AccountDaoImpl.getInstance().add(expected);
        Account actual = AccountDaoImpl.getInstance().getById(expected.getId());
        Assert.assertEquals(expected, actual);
        AccountDaoImpl.getInstance().delete(expected.getId());
    }

    @Test
    public void testGetById() throws Exception {
        Account expected = EntityBuilder.buildAccount(1, "ADMIN", 0, 0);
        Account actual = AccountDaoImpl.getInstance().getById(expected.getId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testUpdateAmount() throws Exception {
        Account expected = EntityBuilder.buildAccount(100, "TEST", 100, 0);
        AccountDaoImpl.getInstance().add(expected);
        double adding = 100;
        expected.setAmount(expected.getAmount() + adding);
        AccountDaoImpl.getInstance().updateAmount(adding, expected.getId());
        Account actual = AccountDaoImpl.getInstance().getById(expected.getId());
        Assert.assertEquals(expected, actual);
        AccountDaoImpl.getInstance().delete(expected.getId());
    }

    @Test
    public void testDelete() throws Exception{
        Account account = EntityBuilder.buildAccount(100, "TEST", 100, 0);
        AccountDaoImpl.getInstance().add(account);
        AccountDaoImpl.getInstance().delete(account.getId());
        Account actual = AccountDaoImpl.getInstance().getById(account.getId());
        Assert.assertNull(actual);
    }
}