package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.utils.EntityBuilder;
import org.junit.*;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class UserDaoImplTest {
    @Ignore
    @Test
    public void testGetInstance() throws Exception {
        UserDaoImpl instance1 = UserDaoImpl.getInstance();
        UserDaoImpl instance2 = UserDaoImpl.getInstance();
        Assert.assertEquals(instance1.hashCode(), instance2.hashCode());
    }

    @Ignore
    @Test
    public void testAdd() throws Exception {
        User expected = EntityBuilder.buildUser(100, "TEST", "TEST", 100, "TEST", "TEST", 0);
        UserDaoImpl.getInstance().add(expected);
        User actual = UserDaoImpl.getInstance().getById(expected.getId());
        Assert.assertEquals(expected, actual);
        UserDaoImpl.getInstance().delete(expected.getId());
    }

    @Ignore
    @Test
    public void testDelete() throws Exception {
        User user = EntityBuilder.buildUser(100, "TEST", "TEST", 100, "TEST", "TEST", 0);
        UserDaoImpl.getInstance().add(user);
        UserDaoImpl.getInstance().delete(user.getId());
        User actual = UserDaoImpl.getInstance().getById(user.getId());
        Assert.assertNull(actual);
    }
}