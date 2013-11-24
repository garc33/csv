package fr.herman.csv.string;

import fr.herman.csv.string.exception.ConversionException;

public abstract class AbstractStringHandler<T> implements StringHandler<T> {

    protected String overridenFormat;

    protected abstract <Q extends T> String getDefaultFormatHelp(Class<Q> type);

    @Override
    public <Q extends T> String getFormatHelp(Class<Q> type) {
        return overridenFormat != null ? overridenFormat
                : getDefaultFormatHelp(type);
    }

    @Override
    public void setOverridenFormat(String format) {
        this.overridenFormat = format;
    }

    @Override
    public String getOverridenFormat() {
        return overridenFormat;
    }

    @Override
    public boolean useQuote() {
        return false;
    }

    @Override
    public final <Q extends T> String marshall(Q object) {
        try {
            return doMarshall(overridenFormat, object);
        } catch (Throwable t) {
            throw new ConversionException(t);
        }
    }

    protected abstract <Q extends T> String doMarshall(String format, Q object) throws Throwable;

    @Override
    public final <Q extends T> T unmarshall(Class<Q> type, String string) {
        try {
            return doUnmarshall(overridenFormat, type, string);
        } catch (Throwable t) {
            throw new ConversionException(t);
        }
    }

    protected abstract <Q extends T> T doUnmarshall(String format,
            Class<Q> type, String string) throws Throwable;
}
