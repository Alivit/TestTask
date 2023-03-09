package service;

import dao.DiscountCardDAO;
import database.DBConnection;
import entity.DiscountCard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiscountCardService implements DiscountCardDAO {

    Connection connection = DBConnection.getConnection();
    PreparedStatement preparedStatement = null;
    Statement statement = null;

    @Override
    public void add(DiscountCard card) throws SQLException {

        String sql = "INSERT INTO discount_card (code, discount) VALUES (?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,card.getCode());
            preparedStatement.setInt(2,card.getDiscount());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<DiscountCard> getAll() throws SQLException {
        List<DiscountCard> cardList = new ArrayList<>();

        String sql = "SELECT * FROM discount_card";

        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                DiscountCard card = new DiscountCard();
                card.setId(resultSet.getInt("id"));
                card.setCode(resultSet.getInt("code"));
                card.setDiscount(resultSet.getInt("discount"));

                cardList.add(card);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cardList;
    }

    @Override
    public void update(DiscountCard card) throws SQLException {
        String sql = "UPDATE discount_card SET code=?, discount=?";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, card.getCode());
            preparedStatement.setInt(2, card.getDiscount());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(DiscountCard card) throws SQLException {
        String sql = "DELETE FROM discount_card WHERE ID=?";

        try{
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,card.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
