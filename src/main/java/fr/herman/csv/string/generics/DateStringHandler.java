package fr.herman.csv.string.generics;

import java.text.SimpleDateFormat;
import java.util.Date;

import fr.herman.csv.string.AbstractStringHandler;

public class DateStringHandler extends AbstractStringHandler<Date> {

    private final static String DEFAULT_FORMAT = "yyyy/MM:dd HH:mm:ss";

    private final static SimpleDateFormat createFormat(String overridenFormat) {
        return new SimpleDateFormat(overridenFormat != null ? overridenFormat
                : DEFAULT_FORMAT);
    }

    @Override
    public Class<Date> getHandledKey() {
        return Date.class;
    }

    @Override
    protected <Q extends Date> String getDefaultFormatHelp(Class<Q> type) {
        return DEFAULT_FORMAT;
    }

    @Override
    protected <Q extends Date> String doMarshall(String format, Q object) {
        return createFormat(format).format(object);
    }

    @Override
    protected <Q extends Date> Date doUnmarshall(String format, Class<Q> type,
            String string) throws Throwable {
        return createFormat(format).parse(string);
    }

}
