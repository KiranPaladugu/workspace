/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2015
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/

package feedReader;
import java.io.*;

import javax.xml.stream.*;
import javax.xml.stream.events.*;


/**
 * Used to parse GET, GET_CONFIG and ACTION Netconf message. Used in QueryResponseReaderAdapter class.
 */
public class QueryResponseReader implements NetconfParser {

    /**
     * Property names used to configure writers produced by Stax2/Woodstox implementation of javax.xml.stream.XMLOutputFactory.
     */

    /**
     * Whether stream writers are allowed to automatically output empty elements, when a start element is immediately followed by matching end
     * element. If true, will output empty elements; if false, will always create separate end element.
     *
     * @see org.codehaus.stax2.XMLOutputFactory2
     */
    public static final String P_AUTOMATIC_EMPTY_ELEMENTS = "org.codehaus.stax2.automaticEmptyElements";

    /**
     * Whether output classes should do basic verification that the output structure is well-formed (start and end elements match); that there is one
     * and only one root, and that there is no textual content in prolog/epilog. If false, won't do any checking regarding structure.
     *
     * @see com.ctc.wstx.api.WstxOutputProperties
     */
    public static final String P_OUTPUT_VALIDATE_STRUCTURE = "com.ctc.wstx.outputValidateStructure";


    private final String tagName;
    private final XMLEventReader reader;
    private final ByteArrayOutputStream data;
    private final XMLInputFactory parserFactory;
    private boolean dataReady;

    public QueryResponseReader(final String tagName, final XMLEventReader reader) {
        this.tagName = tagName;
        this.reader = reader;
        this.data = new ByteArrayOutputStream();
        this.parserFactory = XMLInputFactory.newInstance();
        this.dataReady = false;
    }

    public String getData() throws XMLStreamException {
        if (!dataReady) {
            final XMLOutputFactory output = XMLOutputFactory.newInstance();
            setXMLOutputFactoryProperty(output, P_AUTOMATIC_EMPTY_ELEMENTS, false);
            setXMLOutputFactoryProperty(output, P_OUTPUT_VALIDATE_STRUCTURE, false);
            boolean isTagNameFound = false;
            while (reader.hasNext()) {
                final XMLEvent event = reader.nextEvent();
                if (event.isStartElement() && ((StartElement) event).getName().getLocalPart().equalsIgnoreCase(this.tagName)) {
                    isTagNameFound = true;
//                    writer = output.createXMLEventWriter(data);
                } else if (event.isEndElement() && ((EndElement) event).getName().getLocalPart().equalsIgnoreCase(this.tagName)) {
                    dataReady = true;
                    break;
                } else if (isTagNameFound && event.isCharacters() ) {
                    try {
                        data.write(((Characters)event).getData().getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
                try {
                    data.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return data.toString();
    }

    @Override
    public String parse(final String data) throws Exception {

        final RpcReplyVo rpcReply = new RpcReplyVo();

        try {
            final XMLEventReader reader = parserFactory.createXMLEventReader(new StringReader(data));
            final QueryResponseReader qrReader = new QueryResponseReader("data", reader);

            rpcReply.setData(qrReader.getData());
            return rpcReply.getData();

        } catch (final XMLStreamException e) {
            throw new Exception("Unable parse <get> request response", e);
        }
    }

    private void setXMLOutputFactoryProperty(final XMLOutputFactory factory, final String name, final Object value) {
        if (factory.isPropertySupported(name)) {
            factory.setProperty(name, value);
        }
    }

}
