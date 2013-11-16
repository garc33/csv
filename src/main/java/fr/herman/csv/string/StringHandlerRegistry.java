package fr.herman.csv.string;

import java.lang.reflect.Field;

import fr.herman.csv.registry.GenericRegistry;

public class StringHandlerRegistry extends
        GenericRegistry<StringHandler<?>, Class<?>> implements
        StringHandlerLookup {

    @Override
    protected boolean areKeysEquals(Class key, Class klass) {

        if (key.isAssignableFrom(klass)) {
            return true;
        }

        if (klass.isPrimitive()) {
            try {
                final Field field = key.getField("TYPE");

                if (field != null) {
                    if (klass.equals(field.get(null))) {
                        return true;
                    }
                }
            } catch (final Exception e) {
            }
        }
        return false;
    }

    @Override
    protected StringHandler<?> getDefaultObject(Class klass) {
        return null;
    }
}
