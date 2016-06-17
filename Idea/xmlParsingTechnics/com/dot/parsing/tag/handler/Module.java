/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.dot.parsing.tag.handler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.dot.xml.parsing.tech.AbstractSimpleTagHandler;

public class Module extends AbstractSimpleTagHandler {

    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
    }
    
    @Override
    public Object getTransformedObject() {
        return null;
    }

}
