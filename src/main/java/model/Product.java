package model;

public class Product {
    private int id;
    private String name;
    private int amount;
    private double price;
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

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public Product build(){
            return new Product(this);
        }
    }

}
