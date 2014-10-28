package fr.herman.csv.core

import spock.lang.Specification
import spock.lang.Unroll
import fr.herman.csv.reader.SimpleCsvReader

class ReaderSpec extends Specification {

    @Unroll("read line #input return #output")
    def "read line"(){
        expect:
        new SimpleCsvReader(new StringReader(input), ',' as char, '"'as char, '#'as char).readLine()==output

        where:
        input              |output
        ',,'               |['', '', '']
        'a,a,a'            |['a', 'a', 'a']
        'aa,a,aa'          |['aa', 'a', 'aa']
        'aaa,aaa,aaa'      |['aaa', 'aaa', 'aaa']
        'aaa,",",aaa'      |['aaa', '","', 'aaa']
        'a,b,c,d'          |['a', 'b', 'c', 'd']
        '",,,'             |['",,,']
        '",,,"'            |['",,,"']
        '",,,",",,,"'      |['",,,"', '",,,"']
    }

    def "escape commented line"(){
        given:
        def input = """#a,b,c
                      |aa,b#b
                      |#aaa,bbb,ccc,ddd""".stripMargin().stripIndent()
        when:
        def parser = new SimpleCsvReader(new StringReader(input), ',' as char, '"'as char, '#'as char)

        then:
        parser.readLine()==['aa', 'b#b']
        parser.readLine()==null
    }
}
