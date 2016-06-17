/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.pack.mp2mim.handlers;

import static com.pack.mp2mim.MpToMimGenerator.*;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class DomainExtensionTagHandler extends DefaultTagHandler {
    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    private String domain="";
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        domain = attributes.getValue("domain");
        updateResult();
    }
    
    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
    }
    
    public void updateResult(){
        result = TO_MIM_ATTRIB_REF_NS + CHAR_EQUAL + CHAR_DOUBLE_QUOAT + domain + CHAR_DOUBLE_QUOAT + CHAR_SPACE;
    }
}
