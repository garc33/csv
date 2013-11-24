package fr.herman.csv.string.generics;

import java.io.File;

import fr.herman.csv.string.AbstractStringHandler;

public class FileStringHandler extends AbstractStringHandler<File> {

    @Override
    public Class<File> getHandledKey() {
        return File.class;
    }

    @Override
    protected <Q extends File> String getDefaultFormatHelp(Class<Q> type) {
        return "<filepath>";
    }

    @Override
    protected <Q extends File> String doMarshall(String format, Q object) {
        return object.getAbsolutePath();
    }

    @Override
    protected <Q extends File> File doUnmarshall(String format, Class<Q> type,
            String string) {
        return new File(string);
    }

}
