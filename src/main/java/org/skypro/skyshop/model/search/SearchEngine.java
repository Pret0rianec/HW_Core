package org.skypro.skyshop.model.search;

import org.skypro.skyshop.model.Exception.BestResultNotFound;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class SearchEngine {
    private final Set<Searchable> searchables;

    public SearchEngine() {
        this.searchables = new TreeSet<>(Comparator.comparing(Searchable::getSearchTerm));
    }

    public void add(Searchable element) {
        if (element != null && element.getSearchTerm() != null) {
            searchables.add(element);
        }
    }

    public Set<Searchable> search(String searchTerm) {
        Comparator<Searchable> searchComparator = (o1, o2) -> {
            int len1 = o1.getName().length();
            int len2 = o2.getName().length();
            if (len1 != len2) {
                return Integer.compare(len2, len1);
            }
            return o1.getName().compareTo(o2.getName());
        };

        if (searchTerm == null || searchTerm.isEmpty()) {

            return new TreeSet<>(searchComparator);
        }

        return searchables.stream()
                .filter(searchable -> searchable.getSearchTerm().contains(searchTerm))
                .collect(Collectors.toCollection(() -> new TreeSet<>(searchComparator)));
    }

    public Searchable findBestElement(String search) {
        try {
            if (search == null || search.isEmpty()) {
                throw new BestResultNotFound("Поисковый запрос не может быть пустым");
            }
            Searchable bestElement = null;
            int maxCount = 0;
            for (Searchable searchable : searchables) {
                String term = searchable.getSearchTerm();
                int count = 0;
                int index = term.indexOf(search);
                while (index != -1) {
                    count++;
                    index = term.indexOf(search, index + search.length());
                }
                if (count > maxCount) {
                    maxCount = count;
                    bestElement = searchable;
                }
            }
            if (bestElement == null) {
                throw new BestResultNotFound(search + " - такого объекта нет");
            }
            return bestElement;
        } catch (BestResultNotFound e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}