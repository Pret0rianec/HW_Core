package org.skypro.skyshop.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.*;

public abstract class Product implements Searchable, Comparable<Product> {
    private final String name;
    private final UUID id;

    public Product(UUID id, String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название продукта не может быть пустой строкой или \n" +
                    "null");
        }
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Product o) {
        if (this.name == null && o.name == null) return 0;
        if (this.name == null) return -1;
        if (o.name == null) return 1;
        return this.name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @JsonIgnore
    public String getSearchTerm() {
        return getName();
    }

    @JsonIgnore
    public String getTypeContent() {
        return "PRODUCT";
    }

    @Override
    public String getStringRepresentation() {
        return toString();
    }

    public abstract int getPrice();

    public abstract boolean isSpecial();

    @Override
    public UUID getId() {
        return id;
    }
}
