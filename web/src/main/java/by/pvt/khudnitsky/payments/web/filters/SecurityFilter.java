/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.web.filters;

import by.pvt.khudnitsky.payments.constants.UserType;
import by.pvt.khudnitsky.payments.constants.ConfigsConstants;
import by.pvt.khudnitsky.payments.utils.managers.ConfigurationManagerImpl;
import by.pvt.khudnitsky.payments.web.commands.ICommand;
import by.pvt.khudnitsky.payments.web.commands.factory.CommandFactory;
import by.pvt.khudnitsky.payments.web.commands.user.LoginUserCommand;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SecurityFilter implements Filter {
    public void init(FilterConfig fConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        CommandFactory commandFactory = CommandFactory.getInstance();
        ICommand сommand = commandFactory.defineCommand(httpRequest);
        UserType type = (UserType) session.getAttribute("userType");
        if (type == null) {
            String page = сommand.execute(httpRequest);
            RequestDispatcher dispatcher = request.getRequestDispatcher(page);
            dispatcher.forward(httpRequest, httpResponse);
            return;
        }
        chain.doFilter(request, response);
    }

    public void destroy() {}


}
