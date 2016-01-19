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
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.services.impl.AccountServiceImpl;
import by.pvt.khudnitsky.payments.commands.factory.CommandType;
import by.pvt.khudnitsky.payments.utils.RequestParameterParser;
import by.pvt.khudnitsky.payments.utils.logger.PaymentSystemLogger;
import by.pvt.khudnitsky.payments.managers.ConfigurationManager;
import by.pvt.khudnitsky.payments.managers.MessageManager;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class PaymentCommand extends AbstractCommand {
    private User user;
    private double payment;

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        HttpSession session = request.getSession();
        UserType userType = RequestParameterParser.getUserType(request);
        if(userType == UserType.CLIENT){
            user = RequestParameterParser.getRecordUser(request);
            try {
                if(!AccountServiceImpl.getInstance().checkAccountStatus(user.getAccountId())){
                    payment = RequestParameterParser.getAmountFromPayment(request);
                    if(payment > 0){
                        Account account = AccountServiceImpl.getInstance().getById(user.getAccountId());
                        if(account.getAmount() >= payment){
                            CommandType type = RequestParameterParser.getCommandType(request);
                            String description = type.getValue();
                            AccountServiceImpl.getInstance().payment(user, description, payment);
                            request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.SUCCESS_OPERATION));
                            page = ConfigurationManager.getInstance().getProperty(PagePath.CLIENT_PAYMENT_PAGE_PATH);
                        }
                        else{
                            request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.FAILED_OPERATION));
                            page = ConfigurationManager.getInstance().getProperty(PagePath.CLIENT_PAYMENT_PAGE_PATH);
                        }
                    }
                    else{
                        request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.NEGATIVE_ARGUMENT));
                        page = ConfigurationManager.getInstance().getProperty(PagePath.CLIENT_PAYMENT_PAGE_PATH);
                    }
                }
                else{
                    page = ConfigurationManager.getInstance().getProperty(PagePath.CLIENT_BLOCK_PAGE_PATH);
                }
            }
            catch (ServiceException | SQLException e) {
                PaymentSystemLogger.getInstance().logError(getClass(), e.getMessage());
                page = ConfigurationManager.getInstance().getProperty(PagePath.ERROR_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
            }
            catch (NumberFormatException e){
                PaymentSystemLogger.getInstance().logError(getClass(), e.getMessage());
                request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.INVALID_NUMBER_FORMAT));
                page = ConfigurationManager.getInstance().getProperty(PagePath.CLIENT_PAYMENT_PAGE_PATH);
            }
        }
        else{
            page = ConfigurationManager.getInstance().getProperty(PagePath.INDEX_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}
