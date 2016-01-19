/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.dao;

import by.pvt.khudnitsky.payments.entities.Entity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Describes the abstract class <b>AbstractDao</b>
 * @param <T> - entity of <b>Entity</b>
 * @author khudnitsky
 * @version 1.0
 */

public abstract class AbstractDao<T extends Entity> implements IDao <T>{
    protected Connection connection;
    protected PreparedStatement statement;
    protected ResultSet result;
}
