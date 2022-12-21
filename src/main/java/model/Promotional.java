package model;

public class Promotional extends Product {
    private double newPrice;

    public Promotional(Builder builder, double newPrice) {
        super(builder);
        this.newPrice = newPrice;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }
}
