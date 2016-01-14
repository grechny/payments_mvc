/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.web.commands.user;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.pvt.khudnitsky.payments.dao.constants.UserType;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.services.UserService;
import by.pvt.khudnitsky.payments.web.commands.AbstractCommand;
import by.pvt.khudnitsky.payments.services.constants.ConfigsConstants;
import by.pvt.khudnitsky.payments.services.constants.MessageConstants;
import by.pvt.khudnitsky.payments.services.constants.Parameters;
import by.pvt.khudnitsky.payments.services.utils.logger.PaymentSystemLogger;
import by.pvt.khudnitsky.payments.services.utils.managers.ConfigurationManager;
import by.pvt.khudnitsky.payments.services.utils.managers.MessageManager;

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
            if(UserService.INSTANCE.checkUserAuthorization(login, password)){
                User user = UserService.INSTANCE.getUserByLogin(login);
                UserType userType = UserService.INSTANCE.checkAccessLevel(user);
                session.setAttribute(Parameters.USER, user);
                session.setAttribute(Parameters.USERTYPE, userType);
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
        return page;
    }
}
