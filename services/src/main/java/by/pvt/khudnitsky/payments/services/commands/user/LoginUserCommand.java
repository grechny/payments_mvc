/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.services.commands.user;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.pvt.khudnitsky.payments.dao.constants.UserType;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.services.commands.AbstractCommand;
import by.pvt.khudnitsky.payments.services.connectionpool.ConnectionPool;
import by.pvt.khudnitsky.payments.services.constants.ConfigsConstants;
import by.pvt.khudnitsky.payments.services.constants.MessageConstants;
import by.pvt.khudnitsky.payments.services.constants.Parameters;
import by.pvt.khudnitsky.payments.dao.implementations.UserDao;
import by.pvt.khudnitsky.payments.services.logger.PaymentSystemLogger;
import by.pvt.khudnitsky.payments.services.managers.ConfigurationManager;
import by.pvt.khudnitsky.payments.services.managers.MessageManager;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class LoginUserCommand extends AbstractCommand {

    /* (non-Javadoc)
     * @see by.pvt.khudnitsky.payments.commands.Command#execute(javax.servlet.http.HttpServletRequest)
     */
    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter(Parameters.LOGIN);
        String password = request.getParameter(Parameters.PASSWORD);
        String page = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            if(UserDao.INSTANCE.isAuthorized(connection, login, password)){
                HttpSession session = request.getSession();
                User user = UserDao.INSTANCE.getUserByLogin(connection, login);
                UserType userType = UserDao.INSTANCE.checkAccessLevel(connection, login);
                session.setAttribute(Parameters.USERTYPE, userType);
                session.setAttribute(Parameters.USER, user);
                if(UserType.CLIENT.equals(userType)){
                    page = ConfigurationManager.INSTANCE.getProperty(ConfigsConstants.CLIENT_PAGE_PATH);
                }
                else{
                    page = ConfigurationManager.INSTANCE.getProperty(ConfigsConstants.ADMIN_PAGE_PATH);
                }
            }
            else{
                page = ConfigurationManager.INSTANCE.getProperty(ConfigsConstants.INDEX_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_LOGIN_OR_PASSWORD, MessageManager.INSTANCE.getProperty(MessageConstants.WRONG_LOGIN_OR_PASSWORD));
            }
        }
        catch (SQLException e) {
            PaymentSystemLogger.INSTANCE.logError(getClass(), e.getMessage());
            page = ConfigurationManager.INSTANCE.getProperty(ConfigsConstants.ERROR_PAGE_PATH);
            request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.INSTANCE.getProperty(MessageConstants.ERROR_DATABASE));
        }
        finally {
            if (connection != null){
                ConnectionPool.INSTANCE.releaseConnection(connection);
            }
        }
        return page;
    }
}
