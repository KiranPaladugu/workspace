/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.pack.mp2mim.handlers;

import static com.pack.mp2mim.MpToMimGenerator.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.pack.mp2mim.Version;

public class ImplementsTagHandler extends DefaultTagHandler {
    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String,
     * org.xml.sax.Attributes)
     */
    private String refNameSpace = "";
    private Version version = new Version();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        refNameSpace = attributes.getValue(FROM_ATTRIBUTE_NAME);
        version.setVersion(attributes.getValue(FROM_ATTRIBUTE_VERSION));
        version.setRelease(attributes.getValue(FROM_ATTRIBUTE_RELEASE));
        version.setCorrection(attributes.getValue(FROM_ATTRIBUTE_CORRECTION));
        updateResult();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

    }

    /**
     * 
     */
    private void updateResult() {
        result += TO_MIM_ATTRIB_REF_NS + CHAR_EQUAL + CHAR_DOUBLE_QUOAT + refNameSpace + CHAR_DOUBLE_QUOAT + CHAR_SPACE
                + TO_MIM_ATTRIB_REF_NS_VER + CHAR_EQUAL + CHAR_DOUBLE_QUOAT + version.toString() + CHAR_DOUBLE_QUOAT + CHAR_SPACE
                + XML_SINGLE_LINE_END_CHAR + XML_END_TAG_CHAR;
    }
}
