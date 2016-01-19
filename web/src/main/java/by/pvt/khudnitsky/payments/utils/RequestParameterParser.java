package by.pvt.khudnitsky.payments.utils;

import by.pvt.khudnitsky.payments.commands.factory.CommandType;
import by.pvt.khudnitsky.payments.constants.Parameters;
import by.pvt.khudnitsky.payments.constants.UserType;
import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.entities.Operation;
import by.pvt.khudnitsky.payments.entities.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class RequestParameterParser {
    private RequestParameterParser() {}

    public static User getUser(HttpServletRequest request){
        int id = 0;
        if (request.getParameter(Parameters.USER_ID) != null){
            id = Integer.valueOf(request.getParameter(Parameters.USER_ID));
        }
        int accountId = 0;
        if (request.getParameter(Parameters.ACCOUNT_ID) != null){
            accountId = Integer.valueOf(request.getParameter(Parameters.ACCOUNT_ID));
        }
        int accessLevel = 0;
        if (request.getParameter(Parameters.USER_ACCESS_LEVEL) != null){
            accessLevel = Integer.valueOf(request.getParameter(Parameters.USER_ACCESS_LEVEL));
        }
        String firstName = request.getParameter(Parameters.USER_FIRST_NAME);
        String lastName = request.getParameter(Parameters.USER_LAST_NAME);
        String login = request.getParameter(Parameters.USER_LOGIN);
        String password = request.getParameter(Parameters.USER_PASSWORD);
        User user = EntityBuilder.buildUser(id, firstName, lastName, accountId, login, password, accessLevel);
        return user;
    }

    public static Account getAccount(HttpServletRequest request) throws NumberFormatException {
        int id = Integer.valueOf(request.getParameter(Parameters.ACCOUNT_ID));

        String currency = request.getParameter(Parameters.ACCOUNT_CURRENCY);

        double amount = 0;
        if(request.getParameter(Parameters.AMOUNT) != null){
            amount = Double.valueOf(request.getParameter(Parameters.AMOUNT));
        }

        int status = 0;
        if (request.getParameter(Parameters.ACCOUNT_STATUS) != null){
            status = Integer.valueOf(request.getParameter(Parameters.ACCOUNT_STATUS));
        }

        Account account = EntityBuilder.buildAccount(id, currency, amount, status);
        return account;
    }

    public static UserType getUserType(HttpServletRequest request) {
        return (UserType) request.getSession().getAttribute(Parameters.USERTYPE);
    }

    public static List<Account> getAccountsList(HttpServletRequest request) {
        return (List<Account>) request.getSession().getAttribute(Parameters.ACCOUNTS_LIST);
    }

    public static User getRecordUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(Parameters.USER);
    }

    public static CommandType getCommandType(HttpServletRequest request){
        String commandName = request.getParameter(Parameters.COMMAND);
        CommandType commandType = CommandType.LOGIN;
        if(commandName != null) {
            commandType = CommandType.valueOf(commandName.toUpperCase());
        }
        return commandType;
    }

    public static double getAmountFromFunds(HttpServletRequest request) throws NumberFormatException{
        return Double.valueOf(request.getParameter(Parameters.OPERATION_ADD_FUNDS));
    }

    public static double getAmountFromPayment(HttpServletRequest request) throws NumberFormatException{
        return Double.valueOf(request.getParameter(Parameters.OPERATION_PAYMENT));
    }
}
