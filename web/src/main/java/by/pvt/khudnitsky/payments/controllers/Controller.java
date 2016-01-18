/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.pvt.khudnitsky.payments.commands.factory.CommandFactory;
import by.pvt.khudnitsky.payments.commands.ICommand;
import by.pvt.khudnitsky.payments.constants.PagePath;
import by.pvt.khudnitsky.payments.managers.ConfigurationManager;
import by.pvt.khudnitsky.payments.utils.RequestHandler;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class Controller extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestHandler.processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestHandler.processRequest(request, response);
    }
}
