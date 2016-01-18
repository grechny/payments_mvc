/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.commands.impl.client;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.pvt.khudnitsky.payments.commands.AbstractCommand;
import by.pvt.khudnitsky.payments.constants.*;
import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.managers.MessageManager;
import by.pvt.khudnitsky.payments.services.impl.AccountServiceImpl;
import by.pvt.khudnitsky.payments.utils.RequestParameterParser;
import by.pvt.khudnitsky.payments.utils.logger.PaymentSystemLogger;
import by.pvt.khudnitsky.payments.managers.ConfigurationManager;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class BalanceCommand extends AbstractCommand {
    private User user;

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        HttpSession session = request.getSession();
        UserType userType = RequestParameterParser.getUserType(request);
        if(userType == UserType.CLIENT){
            user = RequestParameterParser.getRecordUser(request);
            try {
                Account account = AccountServiceImpl.getInstance().getById(user.getAccountId());
                request.setAttribute(Parameters.OPERATION_BALANCE, account.getAmount());
                request.setAttribute(Parameters.ACCOUNT_CURRENCY, account.getCurrency());
                page = ConfigurationManager.getInstance().getProperty(PagePath.CLIENT_BALANCE_PAGE_PATH);
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
