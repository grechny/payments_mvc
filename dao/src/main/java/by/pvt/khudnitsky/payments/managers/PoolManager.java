/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.managers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import by.pvt.khudnitsky.payments.constants.ConfigsConstants;
//import by.pvt.khudnitsky.payments.utils.logger.PaymentSystemLogger;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */

public class PoolManager {
    private static PoolManager instance;
    private BasicDataSource dataSource;

    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    private PoolManager() {
        ResourceBundle bundle = ResourceBundle.getBundle("database");
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(bundle.getString("database.driver"));
        dataSource.setUsername(bundle.getString("database.user"));
        dataSource.setPassword(bundle.getString("database.password"));
        dataSource.setUrl(bundle.getString("database.url"));
    }

    public static synchronized PoolManager getInstance(){
        if(instance == null){
            instance = new PoolManager();
        }
        return instance;
    }

    private Connection connect() throws SQLException {
        Connection connection  = dataSource.getConnection();
        return connection;
    }

    public Connection getConnection() throws SQLException {
        if(connectionHolder.get() == null) {
            Connection connection = connect();
            connectionHolder.set(connection);
        }
        System.out.println("Взяли" + connectionHolder.get());
        return connectionHolder.get();
    }

    public void releaseConnection(Connection connection) throws SQLException{
        connection.close();
        connectionHolder.remove();
    }
}

//public class PoolManager {
//    private static PoolManager instance;
//    private static DataSource dataSource;
//    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();
//
//    private PoolManager() {}
//
//    public static synchronized PoolManager getInstance(){
//        if(instance == null){
//            instance = new PoolManager();
//        }
//        return instance;
//    }
//
//    private Connection connect() throws SQLException {
//        Connection connection = null;
//        try{
//            InitialContext initContext = new InitialContext();
//            dataSource = (DataSource) initContext.lookup(ConfigsConstants.DATABASE_SOURCE);
//            connection = dataSource.getConnection();
//        }
//        catch(NamingException e){
//            //PaymentSystemLogger.getInstance().logError(getClass(), e.getMessage());
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//            // TODO logger
//        }
//        catch (Exception e){
//            System.out.println("!!!!");
//        }
//        return connection;
//    }
//
//    public Connection getConnection() throws SQLException {
//        if(connectionHolder.get() == null) {
//            Connection connection = connect();
//            connectionHolder.set(connection);
//        }
//        System.out.println("Взяли" + connectionHolder.get());
//        return connectionHolder.get();
//    }
//
//    public void releaseConnection(Connection connection) throws SQLException{
//        connection.close();
//        connectionHolder.remove();
//    }
//}




