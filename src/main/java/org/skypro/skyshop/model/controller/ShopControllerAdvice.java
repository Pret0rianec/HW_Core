package org.skypro.skyshop.model.controller;

import org.skypro.skyshop.model.Exception.NoSuchProductException;
import org.skypro.skyshop.model.error.ShopError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ShopControllerAdvice {
    @ExceptionHandler(NoSuchProductException.class)
    public ResponseEntity<ShopError> handleNoSuchProductException(NoSuchProductException exception){
        ShopError shopError = new ShopError("404", "PRODUCT_NOT_FOUND");
        return new ResponseEntity<>(shopError, HttpStatus.NOT_FOUND);
    }
}
