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

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;



/**
 * Used to parse GET, GET_CONFIG and ACTION Netconf message.
 */
public class QueryResponseReaderAdapter extends RpcReplyParser {
    private final RpcReplyVo rpcReplyVo;
    private final ByteArrayOutputStream dataHolder;
    private boolean dataTag;

    public QueryResponseReaderAdapter() {
        dataHolder = new ByteArrayOutputStream();
        rpcReplyVo = new RpcReplyVo();
    }

    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if("data".equalsIgnoreCase(localName)){
            dataTag=true;
        }
    }

    @Override
    public void characters(final char[] ch, final int start, final int length) throws SAXException {
        super.characters(ch, start, length);
        if(dataTag){
            try {
                dataHolder.write(new String(ch,start,length).getBytes());
            } catch (IOException e) {
                throw new SAXException(e);
            }
        }

    }

    @Override
    public void endElement(final String uri, final String localName, final String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if("data".equalsIgnoreCase(localName)){
            dataTag = false;
        }
    }

    @Override
    public void error(final SAXParseException e) throws SAXException {
    }

    @Override
    public void fatalError(final SAXParseException e) throws SAXException {
    }

    @Override
    public String parse(final String data) throws Exception {


        final XMLReader parser;
        String responseData = null;
        try {
            responseData = data;
            parser = XMLReaderFactory.createXMLReader();
            parser.setContentHandler(this);
            parser.setErrorHandler(this);
            parser.parse(new InputSource(new StringReader(responseData)));
            if (isError) {
               

            } else {
              /*  final XMLInputFactory parserFactory = XMLInputFactory.newInstance();
                final XMLEventReader reader = parserFactory.createXMLEventReader(new StringReader(responseData));
                final QueryResponseReader queryReader = new QueryResponseReader("data", reader);*/
                rpcReplyVo.setData(dataHolder.toString());
            }
        } catch (final SAXException | IOException ex ) {
            final String errorMessage = "Exception trying to parse the rpc-reply received from the node with data: " + responseData;
            throw new Exception(errorMessage, ex);
        }
        rpcReplyVo.setErrorReceived(isError);
        return isError ? rpcErrorVo.toString()  : rpcReplyVo.getData();
    }
}
