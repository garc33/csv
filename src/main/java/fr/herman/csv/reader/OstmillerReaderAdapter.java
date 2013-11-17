package fr.herman.csv.reader;

import java.io.IOException;
import java.io.InputStream;

import com.Ostermiller.util.ExcelCSVParser;

import fr.herman.csv.CsvContext;

public class OstmillerReaderAdapter implements CsvReader {

    private final ExcelCSVParser parser;

    public OstmillerReaderAdapter(CsvContext context, InputStream is) {
        parser = new ExcelCSVParser(is, context.getSeparator());
        parser.changeQuote(context.getQuote());
        parser.setCommentStart(String.valueOf(context.getComment()));
    }

    @Override
    public String[] readLine() throws IOException {
        return parser.getLine();
    }

}
