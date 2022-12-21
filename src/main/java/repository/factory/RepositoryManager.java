package repository.factory;

import repository.impls.DiscountCardRepository;
import repository.impls.ProductRepository;
import repository.interf.Repository;
import repository.missed.MissedRepository;

public class RepositoryManager {

    public static Repository getRepository(String type) {
        switch(type) {
            case "DISCOUNT_CARD":
                return new DiscountCardRepository();
            case "PRODUCT":
                return new ProductRepository();
            default:
                return new MissedRepository();
        }
    }
}
