package fr.herman.csv;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import com.Ostermiller.util.CSVPrinter;

import fr.herman.csv.mapper.ObjectToCsvMapper;

public class CsvMarshaller<T> {

    private final CsvContext<T> context;

    CsvMarshaller(CsvContext<T> context) {
        this.context = context;
    }

    public void marshall(ObjectToCsvMapper<T> mapper, List<T> objects,
            OutputStream outputStream) {
        CSVPrinter printer = new CSVPrinter(
                new OutputStreamWriter(outputStream), context.getComment(),
                context.getQuote(), context.getSeparator());
        if (context.isWithHeader()) {
            String[] headers = mapper.headersToCsv(context);
            printer.print(headers);
            printer.println();
        }

        for (T object : objects) {
            String[] strings = mapper.toCsv(context, object);
            printer.print(strings);
            printer.println();
        }
    }
}
