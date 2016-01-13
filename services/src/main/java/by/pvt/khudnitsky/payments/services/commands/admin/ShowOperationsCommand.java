/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.services.commands.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.pvt.khudnitsky.payments.dao.constants.UserType;
import by.pvt.khudnitsky.payments.entities.Operation;
import by.pvt.khudnitsky.payments.services.commands.AbstractCommand;
import by.pvt.khudnitsky.payments.services.connectionpool.ConnectionPool;
import by.pvt.khudnitsky.payments.services.constants.ConfigsConstants;
import by.pvt.khudnitsky.payments.services.constants.MessageConstants;
import by.pvt.khudnitsky.payments.services.constants.Parameters;
import by.pvt.khudnitsky.payments.dao.implementations.OperationDao;
import by.pvt.khudnitsky.payments.services.logger.PaymentSystemLogger;
import by.pvt.khudnitsky.payments.services.managers.ConfigurationManager;
import by.pvt.khudnitsky.payments.services.managers.MessageManager;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class ShowOperationsCommand extends AbstractCommand{

    /* (non-Javadoc)
     * @see by.pvt.khudnitsky.payments.commands.Command#execute(javax.servlet.http.HttpServletRequest)
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        HttpSession session = request.getSession();
        UserType userType = (UserType)session.getAttribute(Parameters.USERTYPE);
        if(userType == UserType.ADMINISTRATOR){
            Connection connection = null;
            try{
                connection = ConnectionPool.INSTANCE.getConnection();
                List<Operation> list = OperationDao.INSTANCE.getAll(connection);
                session.setAttribute(Parameters.OPERATIONS_LIST, list);
                page = ConfigurationManager.INSTANCE.getProperty(ConfigsConstants.ADMIN_SHOW_OPERATIONS_PAGE);
            }
            catch (SQLException e) {
                PaymentSystemLogger.INSTANCE.logError(getClass(), e.getMessage());
                page = ConfigurationManager.INSTANCE.getProperty(ConfigsConstants.ERROR_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.INSTANCE.getProperty(MessageConstants.ERROR_DATABASE));
            }
            finally {
                if (connection != null){
                    ConnectionPool.INSTANCE.releaseConnection(connection);
                }
            }
        }
        else{
            page = ConfigurationManager.INSTANCE.getProperty(ConfigsConstants.INDEX_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}
