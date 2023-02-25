package builder.impls;

import builder.interf.TestBuilder;
import model.DiscountCard;

public class DiscountCardTest implements TestBuilder<DiscountCard> {
    private int id = 0;
    private int code = 1234;
    private int discount = 10;

    private DiscountCardTest(){}

    private DiscountCardTest(DiscountCardTest builder){
        this.code = builder.code;
        this.discount = builder.discount;
        this.id = builder.id;
    }

    public static DiscountCardTest aCard() {
        return new DiscountCardTest();
    }

    public DiscountCardTest code(int code){
        return copyWith(b -> b.code = code);
    }

    @Override
    public DiscountCard build() {
        final var server = new DiscountCard();
        server.setId(id);
        server.setCode(code);
        server.setDiscount(discount);
        return null;
    }
}

