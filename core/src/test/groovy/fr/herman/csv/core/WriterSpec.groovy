package fr.herman.csv.core

import spock.lang.Specification
import spock.lang.Unroll
import fr.herman.csv.writer.SimpleCsvWriter
class WriterSpec extends Specification {

    Writer delegate = Mock(Writer)

    def "write a token"(){
        given:
        def writer = new SimpleCsvWriter(delegate, ' ' as char, '')

        when:
        writer.write('hello')

        then:
        1*delegate.write('hello')
    }

    def "append tokens"(){
        given:
        char delimiter = ',' as char
        int charint = delimiter
        def writer = new SimpleCsvWriter(delegate, delimiter, '')

        when:
        writer.write('hello')
        writer.write('world')

        then:
        1*delegate.write('hello')
        1*delegate.write(charint)
        1*delegate.write('world')
    }

    def "write an array of tokens"(){
        given:
        char delimiter = ',' as char
        int charint = delimiter
        def writer = new SimpleCsvWriter(delegate, delimiter, '')

        when:
        writer.write(['hello', 'world'] as String[])

        then:
        1*delegate.write('hello')
        1*delegate.write(charint)
        1*delegate.write('world')
    }

    def "append an array of tokens"(){
        given:
        char delimiter = ',' as char
        int charint = delimiter
        def writer = new SimpleCsvWriter(delegate, delimiter, '')

        when:
        writer.write(['hello', 'world'] as String[])
        writer.write(['hello', 'world'] as String[])

        then:
        2*delegate.write('hello')
        3*delegate.write(charint)
        2*delegate.write('world')
    }

    def "write 2 line"(){
        given:
        char delimiter = ',' as char
        int charint = delimiter
        def writer = new SimpleCsvWriter(delegate, delimiter, '\n')

        when:
        writer.write('hello')
        writer.writeln()
        writer.write(['world', 'hello'] as String[])

        then:
        2*delegate.write('hello')
        1*delegate.write(charint)
        1*delegate.write('world')
        1*delegate.write('\n')
    }

    def "writeln"(){
        given:
        def writer = new SimpleCsvWriter(delegate, ' ' as char, input)

        when:
        writer.writeln()

        then:
        1*delegate.write(input)

        where:
        input << ['\n', '\n\r']
    }

    def "flush call delegate flush"(){
        given:
        def writer = new SimpleCsvWriter(delegate, ' ' as char, '')

        when:
        writer.flush()

        then:
        1 * delegate.flush()
    }

    @Unroll
    def "write #input get '#output'"(){
        given:
        def swriter = new StringWriter()
        def writer = new SimpleCsvWriter(swriter,',' as char,'\n')

        expect:
        writer.write(input as String[])
        writer.flush()
        swriter.toString()==output

        where:
        input                   |output
        ['a', 'b', 'c']         |'a,b,c'
        ['', null, '']          |',,'
        []                      |''
        ['', '']                |','
        ['","', '",']           |'",",",'
    }
}
