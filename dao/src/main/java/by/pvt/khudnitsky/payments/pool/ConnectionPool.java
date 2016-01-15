/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.pool;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import by.pvt.khudnitsky.payments.constants.ConfigsConstants;
//import by.pvt.khudnitsky.payments.utils.logger.PaymentSystemLogger;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class ConnectionPool {
    private DataSource dataSource;
    private Connection connection;
    private static ConnectionPool instance;

    private ConnectionPool() {
        try{
            InitialContext initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup(ConfigsConstants.DATABASE_SOURCE);
        }
        catch(NamingException e){
            //PaymentSystemLogger.getInstance().logError(getClass(), e.getMessage());
        }
    }

    public static synchronized ConnectionPool getInstance(){
        if(instance == null){
            instance = new ConnectionPool();
        }
        return instance;
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
                //PaymentSystemLogger.getInstance().logError(getClass(), e.getMessage());
            }
        }
    }
}
