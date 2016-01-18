package by.pvt.khudnitsky.payments.utils;

import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.entities.Card;
import by.pvt.khudnitsky.payments.entities.Operation;
import by.pvt.khudnitsky.payments.entities.User;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class EntityBuilder {
    private EntityBuilder(){}

    public static Account buildAccount(int id, String currency, double amount, int status){
        Account account = new Account();
        account.setId(id);
        account.setCurrency(currency);
        account.setAmount(amount);
        account.setStatus(status);
        return account;
    }

    public static Operation buildOperation(int id, int userId, int accountId, double amount, String description, String date){
        Operation operation = new Operation();
        operation.setId(id);
        operation.setUserId(userId);
        operation.setAccountId(accountId);
        operation.setAmount(amount);
        operation.setDescription(description);
        operation.setDate(date);
        return operation;
    }

    public static User buildUser(int id, String firstName, String lastName, int aid, String login, String password, int accessLevel){
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAccountId(aid);
        user.setLogin(login);
        user.setPassword(password);
        user.setAccessLevel(accessLevel);
        return user;
    }

    public static Card buildCard(){
        throw new UnsupportedOperationException();
    }
}
