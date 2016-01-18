/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.commands.impl.admin;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.pvt.khudnitsky.payments.commands.AbstractCommand;
import by.pvt.khudnitsky.payments.constants.*;
import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.services.impl.AccountServiceImpl;
import by.pvt.khudnitsky.payments.utils.logger.PaymentSystemLogger;
import by.pvt.khudnitsky.payments.managers.ConfigurationManager;
import by.pvt.khudnitsky.payments.managers.MessageManager;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class UnblockCommand extends AbstractCommand {

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        HttpSession session = request.getSession();
        UserType userType = (UserType)session.getAttribute(Parameters.USERTYPE);
        if(userType == UserType.ADMINISTRATOR){
            if(request.getParameter(Parameters.UNBLOCK) != null){
                int aid = Integer.valueOf(request.getParameter(Parameters.UNBLOCK));
                try {
                    AccountServiceImpl.getInstance().updateAccountStatus(aid, AccountStatus.UNBLOCKED);
                    List<Account> list = AccountServiceImpl.getInstance().getBlockedAccounts();
                    session.setAttribute(Parameters.ACCOUNTS_LIST, list);
                    page = ConfigurationManager.getInstance().getProperty(PagePath.ADMIN_UNBLOCK_PAGE);
                }
                catch (SQLException e) {
                    PaymentSystemLogger.getInstance().logError(getClass(), e.getMessage());
                    page = ConfigurationManager.getInstance().getProperty(PagePath.ERROR_PAGE_PATH);
                    request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
                }
            }
            else if(!((List)session.getAttribute(Parameters.ACCOUNTS_LIST)).isEmpty()){
                request.setAttribute(Parameters.ERROR_EMPTY_CHOICE, MessageManager.getInstance().getProperty(MessageConstants.EMPTY_CHOICE));
                page = ConfigurationManager.getInstance().getProperty(PagePath.ADMIN_UNBLOCK_PAGE);
            }
            else{
                request.setAttribute(Parameters.ERROR_EMPTY_LIST, MessageManager.getInstance().getProperty(MessageConstants.EMPTY_LIST));
                page = ConfigurationManager.getInstance().getProperty(PagePath.ADMIN_UNBLOCK_PAGE);
            }
        }
        else{
            page = ConfigurationManager.getInstance().getProperty(PagePath.INDEX_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}
