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
import by.pvt.khudnitsky.payments.entities.Operation;
import by.pvt.khudnitsky.payments.managers.MessageManager;
import by.pvt.khudnitsky.payments.services.impl.OperationServiceImpl;
import by.pvt.khudnitsky.payments.utils.RequestParameterParser;
import by.pvt.khudnitsky.payments.utils.logger.PaymentSystemLogger;
import by.pvt.khudnitsky.payments.managers.ConfigurationManager;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class ShowOperationsCommand extends AbstractCommand {

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        HttpSession session = request.getSession();
        UserType userType = RequestParameterParser.getUserType(request);
        if(userType == UserType.ADMINISTRATOR){
            try{
                List<Operation> list = OperationServiceImpl.getInstance().getAll();
                session.setAttribute(Parameters.OPERATIONS_LIST, list);
                page = ConfigurationManager.getInstance().getProperty(PagePath.ADMIN_SHOW_OPERATIONS_PAGE);
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
