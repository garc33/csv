package fr.herman.csv.mapper;

import java.util.ArrayList;
import java.util.List;

import fr.herman.csv.CsvBeanProperties;
import fr.herman.csv.CsvContext;
import fr.herman.csv.CsvProperty;

public class DefaultMapper<T> implements Mapper<T> {

    private final CsvBeanProperties<T> csvBeanProperties;

    private DefaultMapper(CsvBeanProperties<T> csvBeanProperties) {
        this.csvBeanProperties = csvBeanProperties;
    }

    @Override
    public String[] toCsv(CsvContext<T> context, T object) {
        if (!csvBeanProperties.getHandledClass().isAssignableFrom(
                object.getClass())) {
            throw new RuntimeException(object.toString() + "not handled");
        }
        CsvProperty[] properties = csvBeanProperties.getProperties();
        List<String> list = new ArrayList<String>(properties.length);
        for (CsvProperty property : properties) {
            list.add(property.getStringValue(context, object));
        }
        return list.toArray(new String[list.size()]);
    }

    @Override
    public T toObject(CsvContext<T> context, String[] values) {
        T object = csvBeanProperties.getNewInstance();
        for (int i = 0; i < csvBeanProperties.getProperties().length; i++) {
            CsvProperty<T> property = csvBeanProperties.getProperties()[i];
            property.setValue(context, object, values[i]);
        }
        return object;
    }

    @Override
    public String[] headersToCsv(CsvContext<T> context) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void handleHeader(CsvContext<T> context, String[] headers) {
        // TODO Auto-generated method stub

    }

}
