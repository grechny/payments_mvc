/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.commands.factory;

import javax.servlet.http.HttpServletRequest;

import by.pvt.khudnitsky.payments.commands.ICommand;
import by.pvt.khudnitsky.payments.commands.impl.user.LoginUserCommand;
import by.pvt.khudnitsky.payments.constants.Parameters;
import by.pvt.khudnitsky.payments.utils.RequestParameterParser;
import org.apache.log4j.Logger;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class CommandFactory {
    private static CommandFactory instance;
    static Logger logger = Logger.getLogger(CommandFactory.class.getName());

    private CommandFactory(){}

    public static synchronized CommandFactory getInstance(){
        if(instance == null){
            instance = new CommandFactory();
        }
        return instance;
    }

    public ICommand defineCommand(HttpServletRequest request){
        ICommand current = null;
        try{
            CommandType type = RequestParameterParser.getCommandType(request);
            current = type.getCurrentCommand();
        }
        catch(IllegalArgumentException e){
            logger.debug("Incorrect command attempt", e);
            current = new LoginUserCommand();
        }
        return current;
    }
}
