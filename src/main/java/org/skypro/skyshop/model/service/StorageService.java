package org.skypro.skyshop.model.service;

import jakarta.annotation.PostConstruct;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StorageService {
    private final Map<UUID, Product> storageProduct = new HashMap<>();
    private final Map<UUID, Article> storageArticle = new HashMap<>();

    public StorageService() {
    }

    @PostConstruct
    private void initStorage() {
        addProduct();
        addArticle();
    }
    public Collection<Searchable> getAllSearchable() {
        Collection<Searchable> allSearchable = new ArrayList<>();
        allSearchable.addAll(getAllProducts());
        allSearchable.addAll(getAllArticles());
        return allSearchable;
    }
    public Map<UUID, Product> getStorageProduct() {
        return storageProduct;
    }

    public Map<UUID, Article> getStorageArticle() {
        return storageArticle;
    }

    public Collection<Product> getAllProducts() {
        return storageProduct.values();
    }

    public Collection<Article> getAllArticles() {
        return storageArticle.values();
    }

    private void addProduct() {
        Product milk = new SimpleProduct(UUID.randomUUID(), "Молоко", 110);
        Product egg = new SimpleProduct(UUID.randomUUID(), "Яйцо", 120);
        Product bread = new SimpleProduct(UUID.randomUUID(), "Хлеб", 40);
        storageProduct.put(milk.getId(), milk);
        storageProduct.put(egg.getId(), egg);
        storageProduct.put(bread.getId(), bread);
    }

    private void addArticle() {
        Article artMilk = new Article(UUID.randomUUID(), "Молочные продукты", "Они содержат много кальция");
        Article artEgg = new Article(UUID.randomUUID(), "Яйцо птицы", "Яйцо называют «природным поливитаминным комплексом»");
        Article artBread = new Article(UUID.randomUUID(), "Хлеб", "Хлеб - всему голова!");
        storageArticle.put(artMilk.getId(), artMilk);
        storageArticle.put(artEgg.getId(), artEgg);
        storageArticle.put(artBread.getId(), artBread);
    }
}
