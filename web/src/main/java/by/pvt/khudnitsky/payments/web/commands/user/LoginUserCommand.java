/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.web.commands.user;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.pvt.khudnitsky.payments.constants.UserType;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.services.impl.UserServiceImpl;
import by.pvt.khudnitsky.payments.web.commands.AbstractCommand;
import by.pvt.khudnitsky.payments.constants.ConfigsConstants;
import by.pvt.khudnitsky.payments.constants.MessageConstants;
import by.pvt.khudnitsky.payments.constants.Parameters;
import by.pvt.khudnitsky.payments.utils.logger.PaymentSystemLogger;
import by.pvt.khudnitsky.payments.utils.managers.ConfigurationManagerImpl;
import by.pvt.khudnitsky.payments.utils.managers.MessageManagerImpl;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class LoginUserCommand extends AbstractCommand {

    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter(Parameters.LOGIN);
        String password = request.getParameter(Parameters.PASSWORD);
        HttpSession session = request.getSession();
        String page = null;
        try {
            if(UserServiceImpl.getInstance().checkUserAuthorization(login, password)){
                User user = UserServiceImpl.getInstance().getUserByLogin(login);
                UserType userType = UserServiceImpl.getInstance().checkAccessLevel(user);
                session.setAttribute(Parameters.USER, user);
                session.setAttribute(Parameters.USERTYPE, userType);
                if(UserType.CLIENT.equals(userType)){
                    page = ConfigurationManagerImpl.getInstance().getProperty(ConfigsConstants.CLIENT_PAGE_PATH);
                }
                else{
                    page = ConfigurationManagerImpl.getInstance().getProperty(ConfigsConstants.ADMIN_PAGE_PATH);
                }
            }
            else{
                page = ConfigurationManagerImpl.getInstance().getProperty(ConfigsConstants.INDEX_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_LOGIN_OR_PASSWORD, MessageManagerImpl.getInstance().getProperty(MessageConstants.WRONG_LOGIN_OR_PASSWORD));
            }
        }
        catch (SQLException e) {
            PaymentSystemLogger.getInstance().logError(getClass(), e.getMessage());
            page = ConfigurationManagerImpl.getInstance().getProperty(ConfigsConstants.ERROR_PAGE_PATH);
            request.setAttribute(Parameters.ERROR_DATABASE, MessageManagerImpl.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
        }
        return page;
    }
}
