package fr.herman.csv.core

import spock.lang.Specification
import fr.herman.csv.CsvContext
import fr.herman.csv.CsvMarshaller
import fr.herman.csv.factories.CsvWriterFactory
import fr.herman.csv.mapper.Mapper
import fr.herman.csv.writer.CsvWriter

class MarshallerSpec extends Specification {
    CsvContext context = Mock(CsvContext)
    Mapper mapper = Mock(Mapper)
    CsvWriter writer = Mock(CsvWriter)
    CsvWriterFactory factory = Mock(CsvWriterFactory)
    OutputStream os = Stub(OutputStream)

    def object1 = new Object()
    def object2 = new Object()

    def setup(){
        factory.create(context, _) >> writer
    }

    def "marshall some objects"(){
        setup:
        def marshaller =  new CsvMarshaller(context, factory)

        when:
        marshaller.marshall(mapper, [object1, object2], os)

        then:
        1 * mapper.toCsv(context, object1) >> ['hello', 'world']
        1 * writer.write(['hello', 'world'])
        1 * writer.writeln()
        then:
        1 * mapper.toCsv(context, object2) >> ['hello', 'world']
        1 * writer.write(['hello', 'world'])
        1 * writer.writeln()
        then:
        1 * writer.flush()
    }

    def "write header"(){
        given:
        def marshaller =  new CsvMarshaller(context, factory)

        when:
        marshaller.marshall(mapper, [object1, object2], os)

        then:
        1 * context.isWithHeader() >> true
        1 * mapper.headersToCsv(context) >> ['Gretting', 'People']
        1 * writer.write(['Gretting', 'People'])
        1 * writer.writeln()

        then:
        2 * mapper.toCsv(context, _) >> ['hello', 'world']
        2 * writer.write(['hello', 'world'])
        2 * writer.writeln()
    }

    def "append do not write header"(){
        given:
        def marshaller =  new CsvMarshaller(context, factory)

        when:
        marshaller.marshall(mapper, [object1, object2], os, true)

        then:
        1 * context.isWithHeader() >> true
        0 * mapper.headersToCsv(context) >> ['Gretting', 'People']
        0 * writer.write(['Gretting', 'People'])
        0 * writer.writeln()

        then:
        2 * mapper.toCsv(context, _) >> ['hello', 'world']
        2 * writer.write(['hello', 'world'])
        2 * writer.writeln()
    }
}
