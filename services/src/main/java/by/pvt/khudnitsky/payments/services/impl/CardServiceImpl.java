package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.entities.Card;
import by.pvt.khudnitsky.payments.services.AbstractService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class CardServiceImpl extends AbstractService<Card> {
    private static CardServiceImpl instance;
    private Connection connection;

    private CardServiceImpl(){}

    public static synchronized CardServiceImpl getInstance(){
        if(instance == null){
            instance = new CardServiceImpl();
        }
        return instance;
    }

    /**
     * Calls Dao add() method
     *
     * @param entity - object of derived class Entity
     * @throws SQLException
     */
    @Override
    public void add(Card entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    /**
     * Calls Dao getAll() method
     *
     * @return list of objects of derived class Entity
     * @throws SQLException
     */
    @Override
    public List<Card> getAll() throws SQLException {
        throw new UnsupportedOperationException();
    }

    /**
     * Calls Dao getById() method
     *
     * @param id - id of entity
     * @return object of derived class Entity
     * @throws SQLException
     */
    @Override
    public Card getById(int id) throws SQLException {
        throw new UnsupportedOperationException();
    }

    /**
     * Calls Dao update() method
     *
     * @param entity - object of derived class Entity
     * @throws SQLException
     */
    @Override
    public void update(Card entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    /**
     * Calls Dao delete() method
     *
     * @param id - id of entity
     * @throws SQLException
     */
    @Override
    public void delete(int id) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
