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
    private RequestParameterParser() {
    }

    public static User getUser(HttpServletRequest request) throws NumberFormatException {
        //TODO обработка исключений
        int id = Integer.valueOf(request.getParameter(Parameters.USER_ID));
        String firstName = request.getParameter(Parameters.USER_FIRST_NAME);
        String lastName = request.getParameter(Parameters.USER_LAST_NAME);
        String login = request.getParameter(Parameters.USER_LOGIN);
        String password = request.getParameter(Parameters.USER_PASSWORD);
        int accountId = Integer.valueOf(request.getParameter(Parameters.ACCOUNT_ID));
        int accessLevel = Integer.valueOf(request.getParameter(Parameters.USER_ACCESS_LEVEL));
        User user = EntityBuilder.buildUser(id, firstName, lastName, accountId, login, password, accessLevel);
        return user;
    }

    public static Account getAccount(HttpServletRequest request) throws NumberFormatException {
        int id = Integer.valueOf(request.getParameter(Parameters.ACCOUNT_ID));
        String currency = request.getParameter(Parameters.ACCOUNT_CURRENCY);
        double amount = Double.valueOf(request.getParameter(Parameters.AMOUNT));
        int status = Integer.valueOf(request.getParameter(Parameters.ACCOUNT_STATUS));
        Account account = EntityBuilder.buildAccount(id, currency, amount, status);
        return account;
    }

    public static Operation getOperation(HttpServletRequest request) throws NumberFormatException {
        int id = Integer.valueOf(request.getParameter(Parameters.OPERATION_ID));
        int userId = Integer.valueOf(request.getParameter(Parameters.USER_ID));
        int accountId = Integer.valueOf(request.getParameter(Parameters.ACCOUNT_ID));
        double amount = Double.valueOf(request.getParameter(Parameters.AMOUNT));
        String description = request.getParameter(Parameters.OPERATION_DESCRIPTION);
        String date = request.getParameter(Parameters.OPERATION_DATE);
        Operation operation = EntityBuilder.buildOperation(id, userId, accountId, amount, description, date);
        return operation;
    }

    public static UserType getUserType(HttpServletRequest request) {
        return (UserType) request.getSession().getAttribute(Parameters.USERTYPE);
    }

    public static int getAccountId(HttpServletRequest request) throws NumberFormatException {
        return Integer.valueOf(request.getParameter(Parameters.OPERATION_UNBLOCK));
    }

    public static List<Account> getAccountsList(HttpServletRequest request) {
        return (List<Account>) request.getSession().getAttribute(Parameters.ACCOUNTS_LIST);
    }

    public static User getRecordUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(Parameters.USER);
    }

    public static CommandType getCommandType(HttpServletRequest request){
        String commandName = request.getParameter(Parameters.COMMAND);
        CommandType commandType = CommandType.valueOf(commandName.toUpperCase());
        return commandType;
    }
}
