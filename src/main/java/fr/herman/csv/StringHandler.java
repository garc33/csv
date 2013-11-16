package fr.herman.csv;

import fr.herman.csv.registry.RegistrableEntity;

public interface StringHandler<T> extends RegistrableEntity<Class<T>> {

    <Q extends T> String getFormatHelp(Class<Q> type);

    <Q extends T> String marshall(Q object);

    <Q extends T> T unmarshall(Class<Q> type, String string);

    void setOverridenFormat(String format);

    String getOverridenFormat();

}
