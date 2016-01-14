/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.services.utils.managers;

import java.util.ResourceBundle;

import by.pvt.khudnitsky.payments.services.constants.ConfigsConstants;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public enum MessageManager implements Manager{
    INSTANCE;

    private final ResourceBundle bundle = ResourceBundle.getBundle(ConfigsConstants.MESSAGES_SOURCE);

    /* (non-Javadoc)
     * @see by.pvt.khudnitsky.payments.managers.Manager#getProperty(java.lang.String)
     */
    @Override
    public String getProperty(String key) {
        return bundle.getString(key);
    }
}
