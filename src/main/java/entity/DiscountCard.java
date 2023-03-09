package entity;

import java.util.Objects;

public class DiscountCard {
    private int id;
    private int code;
    private int discount;

    public DiscountCard(int id, int code, int discount) {
        this.id = id;
        this.code = code;
        this.discount = discount;
    }

    public DiscountCard(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiscountCard)) return false;
        DiscountCard that = (DiscountCard) o;
        return getId() == that.getId() && getCode() == that.getCode() && getDiscount() == that.getDiscount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCode(), getDiscount());
    }

    @Override
    public String toString() {
        return "DiscountCard{" +
                "id=" + id +
                ", code=" + code +
                ", discount=" + discount +
                '}';
    }
}
