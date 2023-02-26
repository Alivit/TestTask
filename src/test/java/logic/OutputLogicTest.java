package logic;

import builder.impls.PromotionalTest;
import model.DiscountCard;
import model.Promotional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.RequestUtil;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OutputLogicTest {

    @Test
    void testGetCheck(){
        RequestUtil request = new RequestUtil();
        ArrayList<Promotional> promotionals = new ArrayList<>();

        promotionals.add(PromotionalTest.aPromotional().build());
        request.setPromotional(promotionals);

        List<String> list = OutputLogic.getReceipt(request);
        assertThat(list.get(13).contains("Паста")).isTrue();
    }

    @Test
    public void testDiscountPrice(){

        RequestUtil request = new RequestUtil();
        ArrayList<DiscountCard> cards = new ArrayList<>();
        List<Integer> codeCard = new ArrayList<>();

        cards.add(new DiscountCard(1,1234,15));
        codeCard.add(1234);

        request.setCards(cards);
        request.setCodeCard(codeCard);

        Assertions.assertEquals(85,OutputLogic.discountCalculation(100,request));
    }

}