/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.utils;

import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.entities.Card;
import by.pvt.khudnitsky.payments.entities.Operation;
import by.pvt.khudnitsky.payments.entities.User;

/**
 * Utility class for entities buildings
 * @author khudnitsky
 * @version 1.0
 */

public class EntityBuilder {
    private EntityBuilder(){}

    /**
     * Creates account
     * @param id - account's id
     * @param currency - account's currency
     * @param amount - amount of money
     * @param status - account's status
     * @return entity of <b>Account</b>
     */
    public static Account buildAccount(int id, String currency, double amount, int status){
        Account account = new Account();
        account.setId(id);
        account.setCurrency(currency);
        account.setAmount(amount);
        account.setStatus(status);
        return account;
    }

    /**
     * Creates operation
     * @param id - operation's id
     * @param userId - user's id
     * @param accountId - account's id
     * @param amount - the value of the operation
     * @param description - operation's description
     * @param date - operation's date
     * @return entity of <b>Operation</b>
     */
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

    /**
     * Creates user
     * @param id - user's id
     * @param firstName - user's first name
     * @param lastName user's last name
     * @param aid - account's id
     * @param login - user's login
     * @param password - user's password
     * @param accessLevel - user's access level (0 - client, 1 - administrator)
     * @return entity of <b>User</b>
     */
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

    /**
     * Creates card
     * NOT REALISED
     * @return throw UnsupportedOperationException()
     */
    public static Card buildCard(){
        throw new UnsupportedOperationException();
    }
}
