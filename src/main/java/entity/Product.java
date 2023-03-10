package entity;

import java.util.Objects;

/**
 * Класс хранящий информацию о продуктах
 */
public class Product {
    /**
     * Поле с айди продукта
     */
    private int id;
    /**
     * Поле с названием продукта
     */
    private String name;
    /**
     * Поле с количеством продукта
     */
    private int amount;
    /**
     * Поле с ценой продукта
     */
    private double price;
    /**
     * Поле со статусом продукта
     */
    private String status;

    public Product() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public Product(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.amount = builder.amount;
        this.price = builder.price;
        this.status = builder.status;
    }

    public static class Builder {
        private int id;
        private String name;
        private int amount;
        private double price;
        private String status;

        public Builder(int id, String name, double price, String status) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.status = status;
        }

        public Builder(){}

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public Product build(){
            return new Product(this);
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return getId() == product.getId() && getAmount() == product.getAmount() && Double.compare(product.getPrice(), getPrice()) == 0 && getName().equals(product.getName()) && Objects.equals(getStatus(), product.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAmount(), getPrice(), getStatus());
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}
