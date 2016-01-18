/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.commands.impl.client;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.pvt.khudnitsky.payments.commands.AbstractCommand;
import by.pvt.khudnitsky.payments.constants.*;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.managers.ConfigurationManager;
import by.pvt.khudnitsky.payments.managers.MessageManager;
import by.pvt.khudnitsky.payments.services.impl.AccountServiceImpl;
import by.pvt.khudnitsky.payments.commands.factory.CommandType;
import by.pvt.khudnitsky.payments.utils.logger.PaymentSystemLogger;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class BlockCommand extends AbstractCommand {
    private static User user;
    private static String description;

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        HttpSession session = request.getSession();
        UserType userType = (UserType)session.getAttribute(Parameters.USERTYPE);
        if(userType == UserType.CLIENT){
            user = (User)session.getAttribute(Parameters.USER);
            int aid = user.getAccountId();
            String commandName = request.getParameter(Parameters.COMMAND);
            CommandType type = CommandType.valueOf(commandName.toUpperCase());
            description = type.getValue();
            try {
                if(!AccountServiceImpl.getInstance().checkAccountStatus(aid)){
                    AccountServiceImpl.getInstance().blockAccount(user, description);
                    request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.SUCCESS_OPERATION));
                    page = ConfigurationManager.getInstance().getProperty(PagePath.CLIENT_BLOCK_PAGE_PATH);
                }
                else{
                    page = ConfigurationManager.getInstance().getProperty(PagePath.CLIENT_BLOCK_PAGE_PATH);
                }
            }
            catch (SQLException e) {
                PaymentSystemLogger.getInstance().logError(getClass(), e.getMessage());
                page = ConfigurationManager.getInstance().getProperty(PagePath.ERROR_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
            }
        }
        else{
            page = ConfigurationManager.getInstance().getProperty(PagePath.INDEX_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}
