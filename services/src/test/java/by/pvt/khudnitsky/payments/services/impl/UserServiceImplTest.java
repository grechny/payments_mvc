package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.constants.UserType;
import by.pvt.khudnitsky.payments.dao.impl.AccountDaoImpl;
import by.pvt.khudnitsky.payments.dao.impl.EntityBuilder;
import by.pvt.khudnitsky.payments.dao.impl.UserDaoImpl;
import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.entities.Operation;
import by.pvt.khudnitsky.payments.entities.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class UserServiceImplTest {
    private Account account;
    private User user;
    private Operation operation;

    @Before
    public void setUp(){
        account = EntityBuilder.buildAccount(100, "TEST", 100, 0);
        user = EntityBuilder.buildUser(100, "TEST", "TEST", account.getId(), "TEST", "TEST", 0);
        operation = EntityBuilder.buildOperation(100, user.getId(), user.getAccountId(), 100, "TEST", "01.01.01");
    }

    @After
    public void tearDown() throws Exception{
        account = null;
        user = null;
        operation = null;
    }

    @Test
    public void testGetInstance() throws Exception {
        UserServiceImpl instance1 = UserServiceImpl.getInstance();
        UserServiceImpl instance2 = UserServiceImpl.getInstance();
        Assert.assertEquals(instance1.hashCode(), instance2.hashCode());
    }

    @Test
    public void testCheckUserAuthorization() throws Exception {
        AccountDaoImpl.getInstance().add(account);
        UserDaoImpl.getInstance().add(user);
        boolean expected = true;
        boolean actual = UserServiceImpl.getInstance().checkUserAuthorization(user.getLogin(), user.getPassword());
        AccountDaoImpl.getInstance().delete(account.getId());
        Assert.assertEquals(new Boolean(expected), new Boolean(actual));
    }

    @Test
    public void testGetUserByLogin() throws Exception {
        AccountDaoImpl.getInstance().add(account);
        UserDaoImpl.getInstance().add(user);
        int userActualId = UserDaoImpl.getInstance().getMaxId();
        user.setId(userActualId);
        User actual = UserServiceImpl.getInstance().getUserByLogin(user.getLogin());
        AccountDaoImpl.getInstance().delete(account.getId());
        Assert.assertEquals(user, actual);
    }

    @Test
    public void testCheckAccessLevel() throws Exception {
        AccountDaoImpl.getInstance().add(account);
        UserDaoImpl.getInstance().add(user);
        UserType actual = UserServiceImpl.getInstance().checkAccessLevel(user);
        AccountDaoImpl.getInstance().delete(account.getId());
        Assert.assertEquals(user.getAccessLevel(), actual.ordinal());
    }

    @Test
    public void testCheckIsNewUser() throws Exception {
        boolean expected = true;
        boolean actual = UserServiceImpl.getInstance().checkIsNewUser(user);
        Assert.assertEquals(new Boolean(expected), new Boolean(actual));
    }

    @Test
    public void testRegistrateUser() throws Exception {
        AccountDaoImpl.getInstance().add(account);
        UserServiceImpl.getInstance().registrateUser(user, account);
        int userActualId = UserDaoImpl.getInstance().getMaxId();
        user.setId(userActualId);
        User actual = UserServiceImpl.getInstance().getUserByLogin(user.getLogin());
        AccountDaoImpl.getInstance().delete(account.getId());
        Assert.assertEquals(user, actual);
    }
}