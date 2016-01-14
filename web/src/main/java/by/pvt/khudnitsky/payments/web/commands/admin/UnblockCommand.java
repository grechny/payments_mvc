/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.web.commands.admin;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.pvt.khudnitsky.payments.dao.constants.UserType;
import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.services.AccountService;
import by.pvt.khudnitsky.payments.web.commands.AbstractCommand;
import by.pvt.khudnitsky.payments.services.constants.AccountStatus;
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
                    AccountService.INSTANCE.updateAccountStatus(aid, AccountStatus.UNBLOCKED);
                    List<Account> list = AccountService.INSTANCE.getBlockedAccounts();
                    session.setAttribute(Parameters.ACCOUNTS_LIST, list);
                    page = ConfigurationManager.INSTANCE.getProperty(ConfigsConstants.ADMIN_UNBLOCK_PAGE);
                }
                catch (SQLException e) {
                    PaymentSystemLogger.INSTANCE.logError(getClass(), e.getMessage());
                    page = ConfigurationManager.INSTANCE.getProperty(ConfigsConstants.ERROR_PAGE_PATH);
                    request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.INSTANCE.getProperty(MessageConstants.ERROR_DATABASE));
                }
            }
            else if(!((List)session.getAttribute(Parameters.ACCOUNTS_LIST)).isEmpty()){
                request.setAttribute(Parameters.ERROR_EMPTY_CHOICE, MessageManager.INSTANCE.getProperty(MessageConstants.EMPTY_CHOICE));
                page = ConfigurationManager.INSTANCE.getProperty(ConfigsConstants.ADMIN_UNBLOCK_PAGE);
            }
            else{
                request.setAttribute(Parameters.ERROR_EMPTY_LIST, MessageManager.INSTANCE.getProperty(MessageConstants.EMPTY_LIST));
                page = ConfigurationManager.INSTANCE.getProperty(ConfigsConstants.ADMIN_UNBLOCK_PAGE);
            }
        }
        else{
            page = ConfigurationManager.INSTANCE.getProperty(ConfigsConstants.INDEX_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}
