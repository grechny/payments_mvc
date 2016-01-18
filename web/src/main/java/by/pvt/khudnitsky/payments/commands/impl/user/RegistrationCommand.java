/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.commands.impl.user;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import by.pvt.khudnitsky.payments.constants.PagePath;
import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.services.impl.UserServiceImpl;
import by.pvt.khudnitsky.payments.commands.AbstractCommand;
import by.pvt.khudnitsky.payments.constants.MessageConstants;
import by.pvt.khudnitsky.payments.constants.Parameters;
import by.pvt.khudnitsky.payments.utils.logger.PaymentSystemLogger;
import by.pvt.khudnitsky.payments.managers.ConfigurationManager;
import by.pvt.khudnitsky.payments.managers.MessageManager;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class RegistrationCommand extends AbstractCommand {
    private static String firstName;
    private static String lastName;
    private static String login;
    private static String password;
    private static String accountIdString;
    private static String currency;

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        // TODO сделать RequestHandler
        firstName = request.getParameter(Parameters.FIRST_NAME);
        lastName = request.getParameter(Parameters.LAST_NAME);
        login = request.getParameter(Parameters.LOGIN);
        password = request.getParameter(Parameters.PASSWORD);
        accountIdString = request.getParameter(Parameters.ACCOUNT_ID);
        currency = request.getParameter(Parameters.CURRENCY);

        int accountId = Integer.valueOf(accountIdString);

        User user = buildUser(firstName, lastName, login, password, accountId);
        Account account = buildAccount(accountId, currency);

        try{
            if(areFieldsFullStocked()){
                if(UserServiceImpl.getInstance().checkIsNewUser(user)){
                    UserServiceImpl.getInstance().registrateUser(user, account);
                    page = ConfigurationManager.getInstance().getProperty(PagePath.REGISTRATION_PAGE_PATH);
                    request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.SUCCESS_OPERATION));
                }
                else{
                    page = ConfigurationManager.getInstance().getProperty(PagePath.REGISTRATION_PAGE_PATH);
                    request.setAttribute(Parameters.ERROR_USER_EXSISTS, MessageManager.getInstance().getProperty(MessageConstants.USER_EXSISTS));
                }
            }
            else{
                request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.EMPTY_FIELDS));
                page = ConfigurationManager.getInstance().getProperty(PagePath.REGISTRATION_PAGE_PATH);
            }
        }
        catch (SQLException e) {
            PaymentSystemLogger.getInstance().logError(getClass(), e.getMessage());
            page = ConfigurationManager.getInstance().getProperty(PagePath.ERROR_PAGE_PATH);
            request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
        }
        catch (NumberFormatException e) {
            PaymentSystemLogger.getInstance().logError(getClass(), e.getMessage());
            request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.INVALID_NUMBER_FORMAT));
            page = ConfigurationManager.getInstance().getProperty(PagePath.REGISTRATION_PAGE_PATH);
        }
        // TODO исправить проверку на null
        catch(NullPointerException e){
            PaymentSystemLogger.getInstance().logError(getClass(), e.getMessage());
            page = ConfigurationManager.getInstance().getProperty(PagePath.INDEX_PAGE_PATH);
        }
        return page;
    }


    //TODO Вынести куда-нибудь
    private boolean areFieldsFullStocked(){
        boolean isFullStocked = false;
        if(!firstName.isEmpty() & !lastName.isEmpty() & !login.isEmpty() & !password.isEmpty() & !accountIdString.isEmpty()){
            isFullStocked = true;
        }
        return isFullStocked;
    }

    // TODO вынести методы на слой сервисов, и создать билдеры
    private User buildUser(String firstName, String lastName, String login, String password, int accountId){
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setLogin(login);
        user.setPassword(password);
        user.setAccountId(accountId);
        return user;
    }

    private Account buildAccount(int accountId, String currency){
        Account account = new Account();
        account.setId(accountId);
        account.setCurrency(currency);
        return account;
    }
}
