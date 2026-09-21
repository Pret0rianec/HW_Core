package org.skypro.skyshop.model.service;

import java.util.List;

public final class UserBasket {
    private final List<BasketItem> items;
    private final int totalPriceBasket;

    public UserBasket(List<BasketItem> items, int totalPriceBasket) {
        this.items = items;
        this.totalPriceBasket = totalPriceBasket;
    }

    public UserBasket(List<BasketItem> items) {
        this(items, calculateTotal(items));
    }

    private static int calculateTotal(List<BasketItem> items) {
        return items.stream()
                .mapToInt(item -> item.getProduct().getPrice() * item.getQuantityProducts())
                .sum();
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public int getTotalPriceBasket() {
        return totalPriceBasket;
    }
}
