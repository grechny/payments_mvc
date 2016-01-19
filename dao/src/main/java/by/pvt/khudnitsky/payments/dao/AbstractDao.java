package by.pvt.khudnitsky.payments.dao;

import by.pvt.khudnitsky.payments.entities.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public abstract class AbstractDao<T extends Entity> implements IDao <T>{
    protected Connection connection;
    protected PreparedStatement statement;
    protected ResultSet result;
}
