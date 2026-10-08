package org.skypro.skyshop.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.Exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.service.BasketService;
import org.skypro.skyshop.model.service.StorageService;
import org.skypro.skyshop.model.service.UserBasket;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {
    @Mock
    private ProductBasket productBasket;
    @Mock
    private StorageService storageService;
    @Mock
    private BasketService basketService;

    @BeforeEach
    void setUp() {
        basketService = new BasketService(productBasket, storageService);
    }

    @Test
    void addingNonExistentProductToCart() {
        UUID nonExistentId = UUID.randomUUID();
        when(storageService.getProductById(nonExistentId)).thenReturn(Optional.empty());
        assertThrows(NoSuchProductException.class, () -> {
            basketService.addProductInBasket(nonExistentId);
        }, "Должно быть выброшено исключение NoSuchProductException, если товар не найден");
        verify(productBasket, never()).addProduct(any());
    }

    @Test
    void addingAnExistingProductCallsTheAddProduct() {
        UUID existentId = UUID.randomUUID();
        Product mockProduct = mock(Product.class);
        when(storageService.getProductById(existentId)).thenReturn(Optional.of(mockProduct));
        basketService.addProductInBasket(existentId);
        verify(productBasket, times(1)).addProduct(existentId);
    }

    @Test
    void getUserBasketReturnsAnEmptyCartIfProductBasketIsEmpty() {
        when(productBasket.getProducts()).thenReturn(Collections.emptyMap());
        UserBasket result = basketService.getUserBasket();
        assertNotNull(result, "Объект UserBasket не должен быть null");
        assertTrue(result.getItems().isEmpty(), "Список товаров в UserBasket должен быть пуст");
        verify(storageService, never()).getProductById(any());
    }

    @Test
    void getUserBasketReturnsTheAppropriateBasketIfProductBasketContainsItems() {
        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();
        Product mockProduct1 = mock(Product.class);
        Product mockProduct2 = mock(Product.class);
        Map<UUID, Integer> stubCartMap = Map.of(productId1, 2, productId2, 5);
        when(productBasket.getProducts()).thenReturn(stubCartMap);
        when(storageService.getProductById(productId1)).thenReturn(Optional.of(mockProduct1));
        when(storageService.getProductById(productId2)).thenReturn(Optional.of(mockProduct2));
        UserBasket result = basketService.getUserBasket();
        assertNotNull(result, "Объект UserBasket не должен быть null");
        assertEquals(2, result.getItems().size(), "В корзине должно быть ровно 2 позиции");

        boolean hasProduct1 = result.getItems().stream()
                .anyMatch(item -> item.getProduct() == mockProduct1);
        assertTrue(hasProduct1, "В корзине должен присутствовать Product1");
        verify(storageService, times(1)).getProductById(productId1);
        verify(storageService, times(1)).getProductById(productId2);
    }
}
