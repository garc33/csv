package fr.herman.csv.mapper;

public interface Mapper<T> extends ObjectToCsvMapper<T>, CsvToObjectMapper<T> {

}
