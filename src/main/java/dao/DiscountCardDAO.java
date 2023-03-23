package dao;

import entity.DiscountCard;

import java.sql.SQLException;
import java.util.List;

public interface DiscountCardDAO {
    void add(DiscountCard card) throws SQLException;

    List<DiscountCard> getAll() throws SQLException;

    void update(DiscountCard card) throws SQLException;

    void remove(DiscountCard card) throws SQLException;
}
