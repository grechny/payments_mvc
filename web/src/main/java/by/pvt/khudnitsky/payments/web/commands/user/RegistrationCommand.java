/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.web.commands.user;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.web.commands.AbstractCommand;
import by.pvt.khudnitsky.payments.services.utils.pool.ConnectionPool;
import by.pvt.khudnitsky.payments.services.constants.ConfigsConstants;
import by.pvt.khudnitsky.payments.services.constants.MessageConstants;
import by.pvt.khudnitsky.payments.services.constants.Parameters;
import by.pvt.khudnitsky.payments.dao.implementations.AccountDao;
import by.pvt.khudnitsky.payments.dao.implementations.UserDao;
import by.pvt.khudnitsky.payments.services.utils.logger.PaymentSystemLogger;
import by.pvt.khudnitsky.payments.services.utils.managers.ConfigurationManager;
import by.pvt.khudnitsky.payments.services.utils.managers.MessageManager;

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

    /* (non-Javadoc)
     * @see by.pvt.khudnitsky.payments.commands.Command#execute(javax.servlet.http.HttpServletRequest)
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        firstName = request.getParameter(Parameters.FIRST_NAME);
        lastName = request.getParameter(Parameters.LAST_NAME);
        login = request.getParameter(Parameters.LOGIN);
        password = request.getParameter(Parameters.PASSWORD);
        accountIdString = request.getParameter(Parameters.ACCOUNT_ID);
        currency = request.getParameter(Parameters.CURRENCY);
        Connection connection = null;
        try{
            connection = ConnectionPool.INSTANCE.getConnection();
            if(areFieldsFullStocked()){
                int accountId = Integer.valueOf(accountIdString);
                if(isNewUser(connection, accountId)){
                    registrate(connection);
                    page = ConfigurationManager.INSTANCE.getProperty(ConfigsConstants.REGISTRATION_PAGE_PATH);
                    request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.INSTANCE.getProperty(MessageConstants.SUCCESS_OPERATION));
                }
                else{
                    page = ConfigurationManager.INSTANCE.getProperty(ConfigsConstants.REGISTRATION_PAGE_PATH);
                    request.setAttribute(Parameters.ERROR_USER_EXSISTS, MessageManager.INSTANCE.getProperty(MessageConstants.USER_EXSISTS));
                }
            }
            else{
                request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.INSTANCE.getProperty(MessageConstants.EMPTY_FIELDS));
                page = ConfigurationManager.INSTANCE.getProperty(ConfigsConstants.REGISTRATION_PAGE_PATH);
            }
        }
        catch (SQLException e) {
            PaymentSystemLogger.INSTANCE.logError(getClass(), e.getMessage());
            page = ConfigurationManager.INSTANCE.getProperty(ConfigsConstants.ERROR_PAGE_PATH);
            request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.INSTANCE.getProperty(MessageConstants.ERROR_DATABASE));
        }
        catch (NumberFormatException e) {
            PaymentSystemLogger.INSTANCE.logError(getClass(), e.getMessage());
            request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.INSTANCE.getProperty(MessageConstants.INVALID_NUMBER_FORMAT));
            page = ConfigurationManager.INSTANCE.getProperty(ConfigsConstants.REGISTRATION_PAGE_PATH);
        }
        catch(NullPointerException e){
            PaymentSystemLogger.INSTANCE.logError(getClass(), e.getMessage());
            page = ConfigurationManager.INSTANCE.getProperty(ConfigsConstants.INDEX_PAGE_PATH);
        }
        finally {
            if (connection != null){
                ConnectionPool.INSTANCE.releaseConnection(connection);
            }
        }
        return page;
    }

    private void registrate(Connection connection) throws SQLException{
        int accountId = Integer.valueOf(accountIdString);
        Account account = new Account();
        account.setId(accountId);
        account.setCurrency(currency);
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAccountId(accountId);
        user.setLogin(login);
        user.setPassword(password);
        AccountDao.INSTANCE.create(connection, account);
        UserDao.INSTANCE.create(connection, user);
    }

    private boolean areFieldsFullStocked(){
        boolean isFullStocked = false;
        if(!firstName.isEmpty() & !lastName.isEmpty() & !login.isEmpty() & !password.isEmpty() & !accountIdString.isEmpty()){
            isFullStocked = true;
        }
        return isFullStocked;
    }

    private boolean isNewUser(Connection connection, int accountId) throws SQLException{
        boolean isNew = false;
        if((AccountDao.INSTANCE.getById(connection, accountId) == null) & (UserDao.INSTANCE.isNewUser(connection, login))){
            isNew = true;
        }
        return isNew;
    }
}
