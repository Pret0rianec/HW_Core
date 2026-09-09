package org.skypro.skyshop.model.product;

import java.util.UUID;

public class FixPriceProduct extends Product {
    private static final int FIXED_PRICE = 99;
    private final UUID id;

    public FixPriceProduct(UUID id, String name) {
        super(id, name);
        this.id = id;
    }

    @Override
    public int getPrice() {
        return FIXED_PRICE;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String toString() {
        return getName() + ": Фиксированная цена " + FIXED_PRICE;
    }

    @Override
    public UUID getId() {
        return id;
    }

}
