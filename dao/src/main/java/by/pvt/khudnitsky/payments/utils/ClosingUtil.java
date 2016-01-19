package by.pvt.khudnitsky.payments.utils;

import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class ClosingUtil {
    static Logger logger = Logger.getLogger(ClosingUtil.class.getName());

    private ClosingUtil(){}

    public static void close(PreparedStatement statement){
        if(statement != null){
            try{
                statement.close();
            }
            catch(SQLException e){
                logger.debug("Statement is already null " + e);
            }
        }
    }

    public static void close(ResultSet resultSet){
        if(resultSet != null){
            try{
                resultSet.close();
            }
            catch(SQLException e){
                logger.debug("ResultSet is already null " + e);
            }
        }
    }
}
