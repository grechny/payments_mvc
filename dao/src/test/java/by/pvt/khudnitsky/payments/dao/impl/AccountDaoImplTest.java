package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.entities.Account;
import org.junit.*;

import java.sql.*;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class AccountDaoImplTest {
    private static Connection connection;
    @BeforeClass
    public static void setUp() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/payments",
                "root", "1208");
        if (connection == null) {
            System.out.println("Нет соединения с БД!");
            System.exit(0);
        }
    }

    @AfterClass
    public static void tearDown() throws Exception {
        try{
            if (connection != null){
                connection.close();
            }
        }
        catch (SQLException e){
            //TODO logger
        }
    }

    /**
     * Utility method, creates entity <b>Account</b>
     * @param id - account id
     * @param currency - account currency (BYR, USD, EUR)
     * @param amount - amount value
     * @param status - account status (1 - admin, 0 - client)
     * @return Account
     */
    private Account createAccountForTest(int id, String currency, double amount, int status){
        Account account = new Account();
        account.setId(id);
        account.setCurrency(currency);
        account.setAmount(amount);
        account.setStatus(status);
        return account;
    }

    @Test
    public void testGetById() throws Exception {
        Account expected = createAccountForTest(1, "ADMIN", 0, 0);
        Account actual = AccountDaoImpl.getInstance().getById(connection, 1);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCreate() throws Exception{
        Account expected = createAccountForTest(100, "TEST", 100, 0);
        AccountDaoImpl.getInstance().add(connection, expected);
        Account actual = AccountDaoImpl.getInstance().getById(connection, expected.getId());
        Assert.assertEquals(expected, actual);
        AccountDaoImpl.getInstance().delete(connection, expected.getId());
    }

    @Test
    public void testDeleteById() throws Exception{
        Account account = createAccountForTest(100, "TEST", 100, 0);
        AccountDaoImpl.getInstance().add(connection, account);
        AccountDaoImpl.getInstance().delete(connection, account.getId());
        Account actual = AccountDaoImpl.getInstance().getById(connection, account.getId());
        Assert.assertNull(actual);
    }

    @Test
    public void testUpdateAmount() throws Exception {
        Account expected = createAccountForTest(100, "TEST", 100, 0);
        AccountDaoImpl.getInstance().add(connection, expected);
        double adding = 100;
        expected.setAmount(expected.getAmount() + adding);
        AccountDaoImpl.getInstance().updateAmount(connection, adding, expected.getId());
        Account actual = AccountDaoImpl.getInstance().getById(connection, expected.getId());
        Assert.assertEquals(expected, actual);
        AccountDaoImpl.getInstance().delete(connection, expected.getId());
    }
}