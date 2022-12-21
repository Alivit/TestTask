package repository.impls;

import model.DiscountCard;
import model.Product;
import repository.interf.Repository;
import util.RequestUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DiscountCardRepository implements Repository {

    @Override
    public void get(ResultSet result, RequestUtil request) throws SQLException {
        while (result.next()){
            request.getCards().add( new DiscountCard(result.getInt("id"),
                    result.getInt("code"),
                    result.getInt("discount")));
        }
    }
}

