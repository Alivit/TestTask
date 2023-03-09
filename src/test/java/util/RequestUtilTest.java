package util;

import database.DBConnection;

import model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
    void testPriceCalculation() {

        Map<Integer, Integer> orderMap = new HashMap<>();
        ArrayList<Product.Builder> product = new ArrayList<>();

        orderMap.put(2, 4);
        product.add(new Product.Builder(2, "Сыр", 2.12, "акция"));

        request.setOrderMap(orderMap);
        request.setProducts(product);
        request.comparison();

        assertThat(request.getPromotional().get(0).getNewPrice()).isEqualTo(8.48);
    }

    @Test
    void testCreateData() throws SQLException {

        DBConnection.init();
        request.workWithBD(request);

        assertThat(request.getProducts().size()).isNotEqualTo(0);

    }



}