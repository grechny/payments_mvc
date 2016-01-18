/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.commands.impl.admin;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.pvt.khudnitsky.payments.constants.*;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.services.impl.UserServiceImpl;
import by.pvt.khudnitsky.payments.commands.AbstractCommand;
import by.pvt.khudnitsky.payments.utils.logger.PaymentSystemLogger;
import by.pvt.khudnitsky.payments.managers.ConfigurationManagerImpl;
import by.pvt.khudnitsky.payments.managers.MessageManagerImpl;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class ShowClientsCommand extends AbstractCommand{

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        HttpSession session = request.getSession();
        UserType userType = (UserType)session.getAttribute(Parameters.USERTYPE);
        if(userType == UserType.ADMINISTRATOR){
            try{
                List<User> list = UserServiceImpl.getInstance().getAll();
                session.setAttribute(Parameters.USER_LIST, list);
                page = ConfigurationManagerImpl.getInstance().getProperty(PagePath.ADMIN_SHOW_CLIENTS_PAGE);
            }
            catch (SQLException e) {
                PaymentSystemLogger.getInstance().logError(getClass(), e.getMessage());
                page = ConfigurationManagerImpl.getInstance().getProperty(PagePath.ERROR_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_DATABASE, MessageManagerImpl.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
            }
        }
        else{
            page = ConfigurationManagerImpl.getInstance().getProperty(PagePath.INDEX_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}
