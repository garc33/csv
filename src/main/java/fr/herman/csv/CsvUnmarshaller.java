package fr.herman.csv;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import com.Ostermiller.util.CSVParser;

import fr.herman.csv.mapper.CsvToObjectMapper;

public class CsvUnmarshaller<T> {

    private final CsvContext<T> context;

    public CsvContext<T> getContext() {
        return context;
    }

    CsvUnmarshaller(CsvContext<T> context) {
        this.context = context;
    }

    public Iterator<T> unmarshall(final CsvToObjectMapper<T> mapper,
            InputStream inputStream) {
        final CSVParser parser = new CSVParser(inputStream,
                context.getSeparator());
        parser.setCommentStart(String.valueOf(context.getComment()));
        parser.changeQuote(context.getQuote());
        if (context.isWithHeader()) {
            try {
                mapper.handleHeader(context, parser.getLine());
            } catch (IOException e) {
            }
        }
        return new Iterator<T>() {
            private String[] line;

            private boolean next = true;

            public boolean hasNext() {
                return step();
            }

            public T next() {
                String[] current = line;
                next = true;
                step();
                return mapper.toObject(context, current);
            }

            public void remove() {
            }

            private boolean step() {
                if (next) {
                    try {
                        line = parser.getLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    next = false;
                }
                return line != null;
            }
        };
    }
}
