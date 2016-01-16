/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.managers;

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
public class PoolManager {
    private static PoolManager instance;
    private static DataSource dataSource;
    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>(){
        public Connection initialValue(){
            Connection connection = null;
            try{
                InitialContext initContext = new InitialContext();
                dataSource = (DataSource) initContext.lookup(ConfigsConstants.DATABASE_SOURCE);
                connection = dataSource.getConnection();
            }
            catch(NamingException e){
                //PaymentSystemLogger.getInstance().logError(getClass(), e.getMessage());
            }
            catch (SQLException e) {
                e.printStackTrace();
                // TODO logger
            }
            return connection;
        }
    };

    private PoolManager() {}

    public static synchronized PoolManager getInstance(){
        if(instance == null){
            instance = new PoolManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException{
        System.out.println("Взяли" + connectionHolder.get());
        return connectionHolder.get();
    }

//    public void releaseConnection(Connection connection) {
//        if(connection != null){
//            try {
//                connection.close();
//            }
//            catch (SQLException e) {
//                //PaymentSystemLogger.getInstance().logError(getClass(), e.getMessage());
//            }
//        }
//    }
}

