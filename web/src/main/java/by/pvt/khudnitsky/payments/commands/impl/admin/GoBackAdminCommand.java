/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.commands.impl.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.pvt.khudnitsky.payments.commands.AbstractCommand;
import by.pvt.khudnitsky.payments.constants.PagePath;
import by.pvt.khudnitsky.payments.constants.UserType;
import by.pvt.khudnitsky.payments.managers.ConfigurationManagerImpl;
import by.pvt.khudnitsky.payments.constants.ConfigConstant;
import by.pvt.khudnitsky.payments.constants.Parameters;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class GoBackAdminCommand extends AbstractCommand {

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        HttpSession session = request.getSession();
        UserType userType = (UserType)session.getAttribute(Parameters.USERTYPE);
        if(userType == UserType.ADMINISTRATOR){
            page = ConfigurationManagerImpl.getInstance().getProperty(PagePath.ADMIN_PAGE_PATH);
        }
        else{
            page = ConfigurationManagerImpl.getInstance().getProperty(PagePath.INDEX_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}
