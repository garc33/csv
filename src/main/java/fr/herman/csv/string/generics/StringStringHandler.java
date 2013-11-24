package fr.herman.csv.string.generics;

import fr.herman.csv.string.AbstractStringHandler;

public class StringStringHandler extends AbstractStringHandler<String> {

    @Override
    public Class<String> getHandledKey() {
        return String.class;
    }

    @Override
    protected <Q extends String> String getDefaultFormatHelp(Class<Q> type) {
        return "<string>";
    }

    @Override
    protected <Q extends String> String doMarshall(String format, Q object) {
        return object;
    }

    @Override
    protected <Q extends String> String doUnmarshall(String format,
            Class<Q> type, String string) {
        return string;
    }

}
