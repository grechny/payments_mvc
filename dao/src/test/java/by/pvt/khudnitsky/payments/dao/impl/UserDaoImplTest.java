package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.entities.Operation;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.managers.PoolManager;
import org.junit.*;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.Assert.*;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class UserDaoImplTest {

    @Test
    public void testGetInstance() throws Exception {
        UserDaoImpl instance1 = UserDaoImpl.getInstance();
        UserDaoImpl instance2 = UserDaoImpl.getInstance();
        Assert.assertEquals(instance1.hashCode(), instance2.hashCode());
    }

    @Test
    public void testAdd() throws Exception {
        User expected = EntityBuilder.buildUser(100, "TEST", "TEST", 100, "TEST", "TEST", 0);
        UserDaoImpl.getInstance().add(expected);
        User actual = UserDaoImpl.getInstance().getById(expected.getId());
        Assert.assertEquals(expected, actual);
        UserDaoImpl.getInstance().delete(expected.getId());
    }

    @Test
    public void testDelete() throws Exception {
        User user = EntityBuilder.buildUser(100, "TEST", "TEST", 100, "TEST", "TEST", 0);
        UserDaoImpl.getInstance().add(user);
        UserDaoImpl.getInstance().delete(user.getId());
        User actual = UserDaoImpl.getInstance().getById(user.getId());
        Assert.assertNull(actual);
    }
}