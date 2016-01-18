/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.managers;

import java.util.ResourceBundle;

import by.pvt.khudnitsky.payments.constants.ConfigConstant;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class ConfigurationManagerImpl implements IManager {
    private final ResourceBundle bundle = ResourceBundle.getBundle(ConfigConstant.CONFIGS_SOURCE);
    private static ConfigurationManagerImpl instance;

    private ConfigurationManagerImpl(){}

    public static synchronized ConfigurationManagerImpl getInstance(){
        if(instance == null){
            instance = new ConfigurationManagerImpl();
        }
        return instance;
    }

    @Override
    public String getProperty(String key){
        return bundle.getString(key);
    }
}
