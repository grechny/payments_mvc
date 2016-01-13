/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.services.connectionpool;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import by.pvt.khudnitsky.payments.services.constants.ConfigsConstants;
import by.pvt.khudnitsky.payments.services.logger.PaymentSystemLogger;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public enum ConnectionPool {
    INSTANCE;

    private DataSource dataSource;
    private Connection connection;

    {
        try{
            InitialContext initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup(ConfigsConstants.DATABASE_SOURCE);
        }
        catch(NamingException e){
            PaymentSystemLogger.INSTANCE.logError(getClass(), e.getMessage());
        }
    }

    public Connection getConnection() throws SQLException{
        connection = dataSource.getConnection();
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if(connection != null){
            try {
                connection.close();
            }
            catch (SQLException e) {
                PaymentSystemLogger.INSTANCE.logError(getClass(), e.getMessage());
            }
        }
    }
}
