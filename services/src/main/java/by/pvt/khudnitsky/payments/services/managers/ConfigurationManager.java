/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.services.managers;

import java.util.ResourceBundle;

import by.pvt.khudnitsky.payments.services.constants.ConfigsConstants;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public enum ConfigurationManager implements Manager{
    INSTANCE;

    private final ResourceBundle bundle = ResourceBundle.getBundle(ConfigsConstants.CONFIGS_SOURCE);

    @Override
    public String getProperty(String key){
        return bundle.getString(key);
    }
}
