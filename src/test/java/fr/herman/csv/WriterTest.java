package fr.herman.csv;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import fr.herman.csv.writer.CsvWriter;
import fr.herman.csv.writer.SimpleCsvWriter;

@RunWith(DataProviderRunner.class)
public class WriterTest {

    private static Object[] add(String expected, String... values) {
        List<String> vals = Arrays.asList(values);
        return new Object[]{expected, vals};
    }

    @DataProvider
    public static Object[][] dataProviderCsvWriter() {
        return new Object[][]{
                // @formatter:off     
                add(",,","","",""),
                add(",,",null,null,null),
                add("a,b,c,d","a","b","c","d"),
                add("aaa,bb,cccc","aaa","bb","cccc"),
                add(""),
                add("",""),
                // @formatter:on
        };
    }

    @Test
    @UseDataProvider("dataProviderCsvWriter")
    public void testCsvReader(String expected, List<String> values) throws Throwable {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        CsvContext context = CsvContext.create();
        CsvWriter writer = new SimpleCsvWriter(context, os);
        String[] array = values.toArray(new String[values.size()]);
        writer.write(array);
        writer.flush();
        Assert.assertEquals(expected, os.toString());
    }

}
