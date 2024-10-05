package com.pokerogue.helper.pokemon.data;

import java.util.HashSet;
import java.util.List;

public abstract class Validator {

    protected static void throwIfNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
    }

    protected static void throwIfIdDuplicates(List<String> ids) {
        HashSet<String> uniqueIds = new HashSet<>(ids);
        if (ids.size() != uniqueIds.size()) {
            throw new IllegalArgumentException("Duplicated Id exists");
        }
    }

    protected static void throwIfDelimiterMisplaced(String data, String delimiter) {
        if (data.startsWith(delimiter) || data.endsWith(delimiter)) {
            throw new IllegalArgumentException(data + "Delimiter misplaced");
        }
    }

    protected static void throwIfDelimiterIsSequential(String data, String delimiter) {
        if (data.contains(delimiter.concat(delimiter))) {
            throw new IllegalArgumentException(data + "Delimiter sequential");
        }
    }
}
