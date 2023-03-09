package builder.impls;

import builder.interf.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.With;
import entity.Promotional;

@AllArgsConstructor
@With
@Builder
public class PromotionalTest implements TestBuilder<Promotional> {

    private double newPrice = 14.44;

    private PromotionalTest() {}

    public static PromotionalTest aPromotional() {
        return new PromotionalTest();
    }

    @Override
    public Promotional build() {
        return new Promotional(ProductTest.aProduct().build(), newPrice);
    }
}
