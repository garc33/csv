package fr.herman.csv.string;


public interface StringHandlerLookup {
    StringHandler<?> lookup(final Class<?> key);
}
