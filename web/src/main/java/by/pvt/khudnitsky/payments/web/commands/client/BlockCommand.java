/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.web.commands.client;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.pvt.khudnitsky.payments.dao.constants.UserType;
import by.pvt.khudnitsky.payments.entities.Operation;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.services.AccountService;
import by.pvt.khudnitsky.payments.web.commands.AbstractCommand;
import by.pvt.khudnitsky.payments.web.commands.factory.CommandType;
import by.pvt.khudnitsky.payments.services.utils.pool.ConnectionPool;
import by.pvt.khudnitsky.payments.services.constants.AccountStatus;
import by.pvt.khudnitsky.payments.services.constants.ConfigsConstants;
import by.pvt.khudnitsky.payments.services.constants.MessageConstants;
import by.pvt.khudnitsky.payments.services.constants.Parameters;
import by.pvt.khudnitsky.payments.dao.implementations.AccountDao;
import by.pvt.khudnitsky.payments.dao.implementations.OperationDao;
import by.pvt.khudnitsky.payments.services.utils.logger.PaymentSystemLogger;
import by.pvt.khudnitsky.payments.services.utils.managers.ConfigurationManager;
import by.pvt.khudnitsky.payments.services.utils.managers.MessageManager;

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
                if(!AccountService.INSTANCE.checkAccountStatus(aid)){
                    AccountService.INSTANCE.blockAccount(user, description);
                    request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.INSTANCE.getProperty(MessageConstants.SUCCESS_OPERATION));
                    page = ConfigurationManager.INSTANCE.getProperty(ConfigsConstants.CLIENT_BLOCK_PAGE_PATH);
                }
                else{
                    page = ConfigurationManager.INSTANCE.getProperty(ConfigsConstants.CLIENT_BLOCK_PAGE_PATH);
                }
            }
            catch (SQLException e) {
                PaymentSystemLogger.INSTANCE.logError(getClass(), e.getMessage());
                page = ConfigurationManager.INSTANCE.getProperty(ConfigsConstants.ERROR_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.INSTANCE.getProperty(MessageConstants.ERROR_DATABASE));
            }
        }
        else{
            page = ConfigurationManager.INSTANCE.getProperty(ConfigsConstants.INDEX_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}
