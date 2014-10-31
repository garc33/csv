package fr.herman.csv;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import fr.herman.csv.exception.UnmarshallingException;
import fr.herman.csv.factories.CsvReaderFactory;
import fr.herman.csv.mapper.CsvToObjectMapper;
import fr.herman.csv.reader.CsvReader;

public class CsvUnmarshaller<T> {

    private final static class UnmarshallerIterator<T> implements Iterator<T> {
        private final CsvToObjectMapper<T> mapper;

        private final CsvReader reader;

        private final CsvContext<T> context;

        private String[] line;

        private boolean next = true;

        private UnmarshallerIterator(CsvToObjectMapper<T> mapper, CsvReader reader, CsvContext<T> context) {
            this.mapper = mapper;
            this.reader = reader;
            this.context = context;
        }

        @Override
        public boolean hasNext() {
            return step();
        }

        @Override
        public T next() {
            String[] current = line;
            next = true;
            step();
            return mapper.toObject(context, current);
        }

        @Override
        public void remove() {
        }

        private boolean step() {
            if (next) {
                try {
                    line = reader.readLine();
                } catch (IOException e) {
                    throw new UnmarshallingException(e);
                }
                next = false;
            }
            return line != null;
        }
    }

    private final CsvContext<T> context;
    private final CsvReaderFactory factory;

    public CsvContext<T> getContext() {
        return context;
    }

    CsvUnmarshaller(CsvContext<T> context,CsvReaderFactory factory) {
        this.context = context;
        this.factory = factory;
    }

    public Iterator<T> unmarshall(final CsvToObjectMapper<T> mapper, InputStream inputStream) throws UnmarshallingException {
        final CsvReader reader = factory.create(context, inputStream);
        if (context.isWithHeader()) {
            try {
                mapper.handleHeader(context, reader.readLine());
            } catch (IOException e) {
                throw new UnmarshallingException("Error during header processing", e);
            }
        }
        return new UnmarshallerIterator<T>(mapper, reader, context);
    }
}
