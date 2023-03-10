package entity;

import java.util.Objects;

/**
 * Класс хранящий продукцию со скидкой
 */
public class Promotional extends Product {
    /**
     * Поле хранит новую цену продукта с учётом скидки
     */
    private double newPrice;

    public Promotional(Builder builder, double newPrice) {
        super(builder);
        this.newPrice = newPrice;
    }

    public Promotional(){
        super();
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Promotional)) return false;
        if (!super.equals(o)) return false;
        Promotional that = (Promotional) o;
        return Double.compare(that.getNewPrice(), getNewPrice()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getNewPrice());
    }

    @Override
    public String toString() {
        return "Promotional{" +
                "id=" + super.getId() +
                ", name='" + super.getName() +
                ", amount=" + super.getAmount() +
                ", price=" + super.getPrice() +
                "newPrice=" + newPrice +
                '}';
    }
}
