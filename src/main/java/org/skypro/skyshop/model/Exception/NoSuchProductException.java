package org.skypro.skyshop.model.Exception;

public class NoSuchProductException extends RuntimeException {
    public NoSuchProductException() {
        super("«Товар не найден»");
    }
}
