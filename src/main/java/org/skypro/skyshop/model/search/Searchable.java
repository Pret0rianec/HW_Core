package org.skypro.skyshop.model.search;

import java.util.UUID;

public interface Searchable {

    String getSearchTerm();

    String getTypeContent();

    String getName();

    default String getStringRepresentation() {
        return getName() + " - " + getTypeContent();
    }

    default UUID getId() {
        return UUID.randomUUID();
    }
}
