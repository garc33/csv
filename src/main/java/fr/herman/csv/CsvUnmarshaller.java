package fr.herman.csv;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import fr.herman.csv.mapper.CsvToObjectMapper;
import fr.herman.csv.reader.CsvReader;
import fr.herman.csv.reader.OstmillerReaderAdapter;

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
        final CsvReader reader = new OstmillerReaderAdapter(context,
                inputStream);
        if (context.isWithHeader()) {
            try {
                mapper.handleHeader(context, reader.readLine());
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
                        line = reader.readLine();
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
