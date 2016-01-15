/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.web.commands.user;

import javax.servlet.http.HttpServletRequest;

import by.pvt.khudnitsky.payments.web.commands.AbstractCommand;
import by.pvt.khudnitsky.payments.constants.ConfigsConstants;
import by.pvt.khudnitsky.payments.utils.managers.ConfigurationManagerImpl;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class LogoutUserCommand extends AbstractCommand{

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManagerImpl.getInstance().getProperty(ConfigsConstants.INDEX_PAGE_PATH);
        request.getSession().invalidate();
        return page;
    }
}
