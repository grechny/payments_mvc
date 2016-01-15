/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.utils.managers;

import java.util.ResourceBundle;

import by.pvt.khudnitsky.payments.constants.ConfigsConstants;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class MessageManagerImpl implements IManager {
    private final ResourceBundle bundle = ResourceBundle.getBundle(ConfigsConstants.MESSAGES_SOURCE);
    private static MessageManagerImpl instance;

    private MessageManagerImpl(){}

    public static synchronized MessageManagerImpl getInstance(){
        if(instance == null){
            instance = new MessageManagerImpl();
        }
        return instance;
    }

    @Override
    public String getProperty(String key) {
        return bundle.getString(key);
    }
}
