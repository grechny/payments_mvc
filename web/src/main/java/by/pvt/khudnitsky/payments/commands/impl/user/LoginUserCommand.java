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
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.managers.ConfigurationManager;
import by.pvt.khudnitsky.payments.managers.MessageManager;
import by.pvt.khudnitsky.payments.services.impl.UserServiceImpl;
import by.pvt.khudnitsky.payments.utils.RequestParameterParser;
import by.pvt.khudnitsky.payments.utils.logger.PaymentSystemLogger;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class LoginUserCommand extends AbstractCommand {
    private User user;

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        HttpSession session = request.getSession();
        try {
            user = RequestParameterParser.getUser(request);
            if(UserServiceImpl.getInstance().checkUserAuthorization(user.getLogin(), user.getPassword())){
                user = UserServiceImpl.getInstance().getUserByLogin(user.getLogin());
                UserType userType = UserServiceImpl.getInstance().checkAccessLevel(user);
                session.setAttribute(Parameters.USER, user);
                session.setAttribute(Parameters.USERTYPE, userType);
                if(UserType.CLIENT.equals(userType)){
                    page = ConfigurationManager.getInstance().getProperty(PagePath.CLIENT_PAGE_PATH);
                }
                else{
                    page = ConfigurationManager.getInstance().getProperty(PagePath.ADMIN_PAGE_PATH);
                }
            }
            else{
                page = ConfigurationManager.getInstance().getProperty(PagePath.INDEX_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_LOGIN_OR_PASSWORD, MessageManager.getInstance().getProperty(MessageConstants.WRONG_LOGIN_OR_PASSWORD));
            }
        }
        catch (ServiceException | SQLException e) {
            PaymentSystemLogger.getInstance().logError(getClass(), e.getMessage());
            page = ConfigurationManager.getInstance().getProperty(PagePath.ERROR_PAGE_PATH);
            request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
        }
        return page;
    }
}
