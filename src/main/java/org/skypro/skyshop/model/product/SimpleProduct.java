package org.skypro.skyshop.model.product;

import java.util.UUID;

public class SimpleProduct extends Product {
    private int regularPrice;
    private final UUID id;

    public SimpleProduct(UUID id, String name, int regularPrice) {
        super(id, name);
        if (regularPrice <= 0) {
            throw new IllegalArgumentException("Цена продукта должна быть строго больше 0");
        }
        this.regularPrice = regularPrice;
        this.id = id;
    }

    @Override
    public int getPrice() {
        return regularPrice;
    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    @Override
    public String toString() {
        return getName() + ": " + getPrice();
    }

    @Override
    public UUID getId() {
        return id;
    }

}