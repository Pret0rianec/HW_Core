package org.skypro.skyshop.model.service;

import org.skypro.skyshop.model.product.Product;

public final class BasketItem {
    private final Product product;
    private final int quantityProducts;

    public BasketItem(Product product, int quantityProducts) {
        if ((product == null)&&(quantityProducts <= 0)) {
            throw new IllegalArgumentException("Неправильно введено значение");
        }
        this.product = product;
        this.quantityProducts = quantityProducts;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantityProducts() {
        return quantityProducts;
    }
}
