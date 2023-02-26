package builder.impls;

import builder.interf.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import model.Product;

@AllArgsConstructor
@With
public class ProductTest implements TestBuilder<Product.Builder> {
    private int id = 1;
    private String name = "Паста";
    private int amount = 1;
    private double price = 1;
    private String status = "default";

    private ProductTest() {
    }

    public static ProductTest aProduct() {
        return new ProductTest();
    }

    @Override
    public Product.Builder build() {
        final var server = new Product.Builder(id, name, price, status);
        server.setAmount(amount);
        ;
        return server;
    }
}
