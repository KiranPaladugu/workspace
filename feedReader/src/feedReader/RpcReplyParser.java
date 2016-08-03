/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2013
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/

package feedReader;

import java.io.IOException;
import java.io.StringReader;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;


/**
 * 
 * @author xvaltda
 */
public class RpcReplyParser extends RpcErrorParser {

    private RpcReplyVo rpcReplyVo;

    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if (localName.equalsIgnoreCase("rpc-reply")) {
            rpcReplyVo = new RpcReplyVo();
            final String messageId = attributes.getValue("message-id");
            if (messageId == null) {
                throw new SAXException(new Exception("Received <rpc-reply> message without a messageId"));
            }
            rpcReplyVo.setMessageId(messageId);
            rpcReplyVo.setOk(false); // defaults to false
        } else if (localName.equalsIgnoreCase("ok")) {
            rpcReplyVo.setOk(true);
        }
    }

    @Override
    public String parse(final String data) throws Exception {
        XMLReader parser;
        
        if(data == null){
            throw new Exception("Cannot parse as the transport response data is null");
        }
            
        try {
            parser = XMLReaderFactory.createXMLReader();
            parser.setContentHandler(this);
            parser.setErrorHandler(this);
            parser.parse(new InputSource(new StringReader(data)));
        } catch (final SAXException | IOException ex) {
            final String errorMessage = "Exception trying to parse the rpc-reply received from the node with data: " + data;
            throw new Exception(errorMessage, ex);
        }

        if (isError) {
            rpcErrorVo.setMessageId(rpcReplyVo.getMessageId());
            rpcErrorVo.setErrorReceived(isError);
            return rpcErrorVo.toString();
        } else {
            rpcReplyVo.setErrorReceived(isError);
            return rpcReplyVo.toString();
        }
    }


}