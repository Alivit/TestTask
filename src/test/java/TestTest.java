import database.DBConnection;
import logic.OutputLogic;
import model.DiscountCard;
import model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.RequestUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TestTest {

    RequestUtil request = new RequestUtil();

    @Test
    public void testDiscountPrice(){

        ArrayList<DiscountCard> cards = new ArrayList<>();
        List<Integer> codeCard = new ArrayList<>();

        cards.add(new DiscountCard(1,1234,15));
        codeCard.add(1234);

        request.setCards(cards);
        request.setCodeCard(codeCard);

        Assertions.assertEquals(85,OutputLogic.discountCalculation(100,request));
    }

    @Test
    void testPersent() {

        Assertions.assertEquals(50, request.percent(100, 50));
    }

    @Test
    void testPrice() {

        Map<Integer, Integer> orderMap = new HashMap<>();
        ArrayList<Product.Builder> product = new ArrayList<>();

        orderMap.put(2, 4);
        product.add(new Product.Builder(2, "Сыр", 2.12, "акция"));

        request.setOrderMap(orderMap);
        request.setProducts(product);

        request.comparison();

        Assertions.assertEquals(8.48, request.getPromotional().get(0).getNewPrice());
    }

    @Test
    void testCreateData() throws SQLException {

        DBConnection.init();
        request.workWithBD(request);

        Assertions.assertEquals(18, request.getProducts().size());

    }

    @Test
    void testParse(){
        String[] args = {"2-3","4-5","6-1","card-1234"};
        RequestUtil.parseRequest(args);

        Assertions.assertEquals(3,request.getOrderMap().size());
        Assertions.assertEquals(1,request.getCodeCard().size());
        
    }
}