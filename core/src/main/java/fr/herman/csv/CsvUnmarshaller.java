package fr.herman.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import fr.herman.csv.exception.UnmarshallingException;
import fr.herman.csv.mapper.CsvToObjectMapper;
import fr.herman.csv.reader.CsvReader;
import fr.herman.csv.reader.SimpleCsvReader;

public class CsvUnmarshaller<T>
{

    private final static class UnmarshallerIterator<T> implements Iterator<T>
    {
        private final CsvToObjectMapper<T> mapper;

        private final CsvReader            reader;

        private final CsvContext<T>        context;

        private String[]                   line;

        private boolean                    next = true;

        private UnmarshallerIterator(CsvToObjectMapper<T> mapper,
            CsvReader reader, CsvContext<T> context)
        {
            this.mapper = mapper;
            this.reader = reader;
            this.context = context;
        }

        @Override
        public boolean hasNext()
        {
            return step();
        }

        @Override
        public T next()
        {
            String[] current = line;
            next = true;
            step();
            return mapper.toObject(context, current);
        }

        @Override
        public void remove()
        {
        }

        private boolean step()
        {
            if (next)
            {
                try
                {
                    line = reader.readLine();
                }
                catch (IOException e)
                {
                    throw new UnmarshallingException(e);
                }
                next = false;
            }
            return line != null;
        }
    }

    private final CsvContext<T> context;

    public CsvContext<T> getContext()
    {
        return context;
    }

    CsvUnmarshaller(CsvContext<T> context)
    {
        this.context = context;
    }

    public Iterator<T> unmarshall(final CsvToObjectMapper<T> mapper,
        InputStream inputStream) throws UnmarshallingException
    {
        final CsvReader reader = new SimpleCsvReader(new BufferedReader(new InputStreamReader(inputStream)),
            context.getSeparator(), context.getQuote(), context.getComment());
        if (context.isWithHeader())
        {
            try
            {
                mapper.handleHeader(context, reader.readLine());
            }
            catch (IOException e)
            {
                throw new UnmarshallingException(
                    "Error during header processing", e);
            }
        }
        return new UnmarshallerIterator<T>(mapper, reader, context);
    }
}
