package model;

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
}
