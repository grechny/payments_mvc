/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.entities.Card;
import by.pvt.khudnitsky.payments.constants.ColumnNames;
import by.pvt.khudnitsky.payments.constants.SqlRequests;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class CardDaoImpl extends AbstractDao<Card> {
    private static CardDaoImpl instance;

    private CardDaoImpl(){}

    public static synchronized CardDaoImpl getInstance(){
        if(instance == null){
            instance = new CardDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Card> getAll(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SqlRequests.GET_ALL_CARDS);
        ResultSet result = statement.executeQuery();
        List<Card> list = new ArrayList<>();
        while(result.next()){
            Card card = new Card();
            card.setId(result.getInt(ColumnNames.CARD_ID));
            card.setAccountId(result.getInt(ColumnNames.ACCOUNT_ID));
            card.setValidity(result.getString(ColumnNames.CARD_VALIDITY));
            list.add(card);
        }
        return list;
    }

	    @Override
    public void add(Connection connection, Card entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Card getById(Connection connection, int id) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Connection connection, int id) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
