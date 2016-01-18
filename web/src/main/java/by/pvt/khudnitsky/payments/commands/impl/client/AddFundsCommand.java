/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.commands.impl.client;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.pvt.khudnitsky.payments.commands.AbstractCommand;
import by.pvt.khudnitsky.payments.commands.factory.CommandType;
import by.pvt.khudnitsky.payments.constants.*;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.managers.ConfigurationManagerImpl;
import by.pvt.khudnitsky.payments.managers.MessageManagerImpl;
import by.pvt.khudnitsky.payments.services.impl.AccountServiceImpl;
import by.pvt.khudnitsky.payments.utils.logger.PaymentSystemLogger;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class AddFundsCommand extends AbstractCommand {

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        HttpSession session = request.getSession();
        UserType userType = (UserType)session.getAttribute(Parameters.USERTYPE);
        if(userType == UserType.CLIENT) {
            User user = (User) session.getAttribute(Parameters.USER);
            int aid = user.getAccountId();
            try {
                if (!AccountServiceImpl.getInstance().checkAccountStatus(aid)) {
                    double amount = Double.valueOf(request.getParameter(Parameters.ADD_FUNDS));
                    if (amount > 0) {
                        String commandName = request.getParameter(Parameters.COMMAND);
                        CommandType type = CommandType.valueOf(commandName.toUpperCase());
                        String description = type.getValue();
                        AccountServiceImpl.getInstance().addFunds(user, description, amount);
                        request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManagerImpl.getInstance().getProperty(MessageConstants.SUCCESS_OPERATION));
                        page = ConfigurationManagerImpl.getInstance().getProperty(PagePath.CLIENT_ADDFUNDS_PAGE_PATH);
                    } else {
                        request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManagerImpl.getInstance().getProperty(MessageConstants.NEGATIVE_ARGUMENT));
                        page = ConfigurationManagerImpl.getInstance().getProperty(PagePath.CLIENT_ADDFUNDS_PAGE_PATH);
                    }
                } else {
                    page = ConfigurationManagerImpl.getInstance().getProperty(PagePath.CLIENT_BLOCK_PAGE_PATH);
                }
            } catch (SQLException e) {
                PaymentSystemLogger.getInstance().logError(getClass(), e.getMessage());
                page = ConfigurationManagerImpl.getInstance().getProperty(PagePath.ERROR_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_DATABASE, MessageManagerImpl.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
            } catch (NumberFormatException e) {
                PaymentSystemLogger.getInstance().logError(getClass(), e.getMessage());
                request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManagerImpl.getInstance().getProperty(MessageConstants.INVALID_NUMBER_FORMAT));
                page = ConfigurationManagerImpl.getInstance().getProperty(PagePath.CLIENT_ADDFUNDS_PAGE_PATH);

            }
        }
        else{
            page = ConfigurationManagerImpl.getInstance().getProperty(PagePath.INDEX_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}
