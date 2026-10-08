package org.skypro.skyshop.serviceTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;
import org.skypro.skyshop.model.service.SearchService;
import org.skypro.skyshop.model.service.StorageService;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {
    @Mock
    private StorageService storageService;
    private SearchService searchService;

    @BeforeEach
    void setUp() {
        searchService = new SearchService(storageService);
    }

    @Test
    void searchWhenObjectsAreMissingInStorageService() {
        when(storageService.getAllSearchable()).thenReturn(Collections.emptyList());
        Collection<SearchResult> result = searchService.search("peach");
        assertNotNull(result, "Результат не должен быть null");
        assertTrue(result.isEmpty(), "Коллекция должна быть пустой, так как хранилище пусто");
        verify(storageService, times(1)).getAllSearchable();
    }

    @Test
    void searchWhenObjectsExistInStorageServiceButNoSuitableOneIsFound() {
        Searchable item1 = mock(Searchable.class);
        Searchable item2 = mock(Searchable.class);
        when(item1.getSearchTerm()).thenReturn("молоко");
        when(item2.getSearchTerm()).thenReturn("Моло");
        when(storageService.getAllSearchable()).thenReturn(List.of(item1, item2));
        Collection<SearchResult> result = searchService.search("хлеб");
        assertNotNull(result, "The result must not be null.");
        assertTrue(result.isEmpty(), "The collection must be empty, as there are no matches.");
        verify(storageService, times(1)).getAllSearchable();
    }

    @Test
    void searchWhenASuitableObjectExistsInStorageService() {
        Searchable matchingItem = mock(Searchable.class);
        Searchable nonMatchingItem = mock(Searchable.class);
        Searchable nullTermItem = mock(Searchable.class);
        when(matchingItem.getSearchTerm()).thenReturn("Моло");
        when(nonMatchingItem.getSearchTerm()).thenReturn("мол");
        when(nullTermItem.getSearchTerm()).thenReturn(null);
        when(storageService.getAllSearchable()).thenReturn(List.of(matchingItem, nonMatchingItem, nullTermItem));
        Collection<SearchResult> result = searchService.search("мОлО");
        assertNotNull(result, "The result must not be null.");
        assertEquals(1, result.size(), "Должен быть найден ровно один объект");
    }
}
