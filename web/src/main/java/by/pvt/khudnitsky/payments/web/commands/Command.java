/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.web.commands;

import javax.servlet.http.HttpServletRequest;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public interface Command {
    String execute(HttpServletRequest request);
}
