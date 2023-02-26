package builder.impls;

import builder.interf.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import model.Product;

@AllArgsConstructor
@With
public class ProductTest implements TestBuilder<Product> {
    private int id = 1;
    private String name = "";
    private int amount = 1;
    private double price = 1;
    private String status = "default";

    private ProductTest(){}

    public static ProductTest aProduct(){
        return new ProductTest();
    }

    @Override
    public Product build() {
        final var server = new Product();
        server.setId(id);
        server.setName(name);
        server.setAmount(amount);
        server.setPrice(price);
        server.setStatus(status);
        return server;
    }
}
