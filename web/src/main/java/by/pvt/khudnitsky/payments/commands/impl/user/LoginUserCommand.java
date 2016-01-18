/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.commands.impl.user;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.pvt.khudnitsky.payments.commands.AbstractCommand;
import by.pvt.khudnitsky.payments.constants.*;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.managers.ConfigurationManagerImpl;
import by.pvt.khudnitsky.payments.managers.MessageManagerImpl;
import by.pvt.khudnitsky.payments.services.impl.UserServiceImpl;
import by.pvt.khudnitsky.payments.utils.logger.PaymentSystemLogger;

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
                    page = ConfigurationManagerImpl.getInstance().getProperty(PagePath.CLIENT_PAGE_PATH);
                }
                else{
                    page = ConfigurationManagerImpl.getInstance().getProperty(PagePath.ADMIN_PAGE_PATH);
                }
            }
            else{
                page = ConfigurationManagerImpl.getInstance().getProperty(PagePath.INDEX_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_LOGIN_OR_PASSWORD, MessageManagerImpl.getInstance().getProperty(MessageConstants.WRONG_LOGIN_OR_PASSWORD));
            }
        }
        catch (SQLException e) {
            PaymentSystemLogger.getInstance().logError(getClass(), e.getMessage());
            page = ConfigurationManagerImpl.getInstance().getProperty(PagePath.ERROR_PAGE_PATH);
            request.setAttribute(Parameters.ERROR_DATABASE, MessageManagerImpl.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
        }
        return page;
    }
}
