package org.skypro.skyshop.model.service;
import org.skypro.skyshop.model.search.SearchResult;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class SearchService {
    private final StorageService storageService;

    public SearchService(StorageService storageService) {
        this.storageService = storageService;
    }

    public Collection<SearchResult> search (String searchBar){
        return storageService.getAllSearchable().stream()
                .filter(searchable -> searchable.getSearchTerm() !=null &&
                        searchable.getSearchTerm().toLowerCase().contains(searchBar.toLowerCase()))
                .map(SearchResult::fromSearchable)
                .collect(Collectors.toList());
    }
}
