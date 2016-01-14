/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.web.commands.factory;

import javax.servlet.http.HttpServletRequest;

import by.pvt.khudnitsky.payments.web.commands.Command;
import by.pvt.khudnitsky.payments.web.commands.user.LoginUserCommand;
import by.pvt.khudnitsky.payments.services.constants.Parameters;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public enum CommandFactory {
    INSTANCE;

    public Command defineCommand(HttpServletRequest request){
        Command current = null;
        String commandName = request.getParameter(Parameters.COMMAND);
        try{
            CommandType type = CommandType.valueOf(commandName.toUpperCase());
            current = type.getCurrentCommand();
        }
        catch(NullPointerException | IllegalArgumentException e){
            current = new LoginUserCommand();
            // TODO сделать простой if
        }
        return current;
    }
}
