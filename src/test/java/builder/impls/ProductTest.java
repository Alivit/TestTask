package builder.impls;

import builder.interf.TestBuilder;
import model.Product;

@AllArgsConstructor
@NoArgsConstructor(staticName = "aProduct")
@With
public class ProductTest implements TestBuilder<Product> {
    private int id = 1;
    private String name = "";
    private int amount = 1;
    private double price = 1;
    private String status = "default";

    @Override
    public Product build() {
        final var server = new Product();
        server.setId(id);
        server.setName(name);
        server.setAmount(amount);
        server,setPrice(price);
        server.setStatus(status);
        return server;
    }
}
