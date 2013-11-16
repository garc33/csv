package fr.herman.csv.string;

import fr.herman.csv.StringHandler;

public interface StringHandlerLookup {
    StringHandler<?> lookup(final Class<?> key);
}
