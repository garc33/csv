package fr.herman.csv.string.generics;

import fr.herman.csv.string.AbstractStringHandler;
import fr.herman.csv.string.exception.UnhandledTypeException;

public class NumberStringHandler extends AbstractStringHandler<Number> {

    @Override
    public Class<Number> getHandledKey() {
        return Number.class;
    }

    @Override
    protected <Q extends Number> String getDefaultFormatHelp(Class<Q> type) {
        return "<number>";
    }

    @Override
    protected <Q extends Number> String doMarshall(String format, Q object) throws Throwable {
        return object.toString();
    }

    @Override
    protected <Q extends Number> Number doUnmarshall(String format,
            Class<Q> type, String string) throws Throwable {
        if (Integer.class.equals(type)) {
            return Integer.valueOf(string);
        } else if (Long.class.equals(type)) {
            return Long.valueOf(string);
        } else if (Double.class.equals(type)) {
            return Double.valueOf(string);
        } else if (Float.class.equals(type)) {
            return Float.valueOf(string);
        }
        throw new UnhandledTypeException(type, this);
    }

}
