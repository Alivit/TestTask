package service;

import database.DBConnection;
import entity.DiscountCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountCardServiceTest {

    DiscountCardService discountCardService = null;
    private static Stream<Arguments> cardArgumentsProvider(){
        return Stream.of(
                Arguments.of(new DiscountCard(12, 9735, 7))
        );
    }
    @BeforeEach
    void init() throws FileNotFoundException {
        DBConnection.init();
        discountCardService = new DiscountCardService();
    }
    @ParameterizedTest
    @MethodSource("cardArgumentsProvider")
    public void addTest(DiscountCard card) throws SQLException {
        discountCardService.add(card);
        assertThat(discountCardService.preparedStatement.toString().contains("9735")).isTrue();

    }

    @Test
    public void getAllTest() throws SQLException {
        List<DiscountCard> cardList = discountCardService.getAll();
        assertThat(cardList.size()).isNotEqualTo(0);
    }

    @ParameterizedTest
    @MethodSource("cardArgumentsProvider")
    public void removeTest(DiscountCard card) throws SQLException {
        discountCardService.remove(card);
        assertThat(discountCardService.preparedStatement.toString().contains("12")).isTrue();
    }

    @ParameterizedTest
    @MethodSource("cardArgumentsProvider")
    public void updateTest(DiscountCard card) throws SQLException {
        discountCardService.update(card);
        assertThat(discountCardService.preparedStatement.toString().contains("7")).isTrue();
    }

}