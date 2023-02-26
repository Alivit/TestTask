package util;

import database.DBConnection;
import logic.OutputLogic;
import model.DiscountCard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RequestUtilTest {

    RequestUtil request = new RequestUtil();

    private static Stream<Arguments> requestArgumentsProvider(){
        return Stream.of(
                Arguments.of((Object) new String[]{"2-3", "4-5", "6-1", "card-1234"}),
                Arguments.of((Object) new String[]{"8-3", "card-8888"}),
                Arguments.of((Object) new String[]{"8-3","11-2", "card-7777"})
        );
    }

    @ParameterizedTest
    @MethodSource("requestArgumentsProvider")
    void testParse(String[] args){
        RequestUtil.parseRequest(args);
        assertThat(request.getOrderMap().size()).isNotEqualTo(0);
        assertThat(request.getCodeCard().size()).isNotEqualTo(0);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    void testPercent(double sum, double discount) {
        assertThat(request.percent(sum, (int) discount)).isEqualTo(50);
    }

    @Test
    public void testDiscountPrice(){

        ArrayList<DiscountCard> cards = new ArrayList<>();
        List<Integer> codeCard = new ArrayList<>();

        cards.add(new DiscountCard(1,1234,15));
        codeCard.add(1234);

        request.setCards(cards);
        request.setCodeCard(codeCard);

        Assertions.assertEquals(85, OutputLogic.discountCalculation(100,request));
    }



//    @Test
//    void testPriceCalculation() {
//
//        Map<Integer, Integer> orderMap = new HashMap<>();
//        ArrayList<Product> product = new ArrayList<>();
//
//        orderMap.put(2, 4);
//        product.add(ProductTest.aProduct().build());
//
//        request.setOrderMap(orderMap);
//
//        request.comparison();
//
//        Assertions.assertEquals(8.48, request.getPromotional().get(0).getNewPrice());
//    }

    @Test
    void testCreateData() throws SQLException {

        DBConnection.init();
        request.workWithBD(request);

        assertThat(request.getProducts().size()).isNotEqualTo(0);

    }



}