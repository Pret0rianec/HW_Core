package org.skypro.skyshop.model.service;

import org.skypro.skyshop.model.Exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.product.Product;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class BasketService {
    private final ProductBasket productBasket;
    private final StorageService storageService;

    public BasketService(ProductBasket productBasket, StorageService storageService) {
        this.productBasket = productBasket;
        this.storageService = storageService;
    }

    public ProductBasket getProductBasket() {
        return productBasket;
    }

    public StorageService getStorageService() {
        return storageService;
    }

    public void addProductInBasket(UUID id) {
        Optional<Product> productOptional = getStorageService().getProductById(id);
        if (!productOptional.isPresent()) {
            throw new NoSuchProductException();
        }
        getProductBasket().addProduct(id);
    }

    public UserBasket getUserBasket() {
        Map<UUID, Integer> cartMap = getProductBasket().getProducts();
        List<BasketItem> basketItems = cartMap.entrySet().stream()
                .map(entry -> {
                    UUID productId = entry.getKey();
                    int quantity = entry.getValue();
                    Product product = storageService.getProductById(productId)
                            .orElseThrow(() -> new NoSuchProductException());

                    return new BasketItem(product, quantity);
                })
                .toList();
        return new UserBasket(basketItems);
    }
}
