package entity;

import java.util.Objects;

/**
 * Класс хранящий информацию о дисконтных картах
 */
public class DiscountCard {
    /**
     * Поле с айди карты
     */
    private int id;
    /**
     * Поле с кодом карты
     */
    private String code;
    /**
     * Поле с размером скидки карты
     */
    private int discount;

    public DiscountCard(int id, String code, int discount) {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
