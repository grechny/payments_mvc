/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.utils.logger;

import org.apache.log4j.Logger;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class PaymentSystemLogger {
    private Logger logger;
    private static PaymentSystemLogger instance;

    private PaymentSystemLogger(){}

    public static synchronized PaymentSystemLogger getInstance(){
        if(instance == null){
            instance = new PaymentSystemLogger();
        }
        return instance;
    }

    public void logError(Class sender, String message){
        logger = Logger.getLogger(sender);
        logger.error(message);
    }


}
