/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.commands.factory;

import javax.servlet.http.HttpServletRequest;

import by.pvt.khudnitsky.payments.commands.ICommand;
import by.pvt.khudnitsky.payments.commands.impl.user.LoginUserCommand;
import by.pvt.khudnitsky.payments.constants.Parameters;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class CommandFactory {
    private static CommandFactory instance;

    private CommandFactory(){}

    public static synchronized CommandFactory getInstance(){
        if(instance == null){
            instance = new CommandFactory();
        }
        return instance;
    }

    public ICommand defineCommand(HttpServletRequest request){
        ICommand current = null;
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
