/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.pack.mp2mim.handlers;

import static com.pack.mp2mim.MpToMimGenerator.*;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class MimHandler extends DefaultTagHandler {
    //    private Version version = new Version();
    private String nameSpace = "";

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String,
     * org.xml.sax.Attributes)
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        nameSpace = attributes.getValue(FROM_ATTRIBUTE_NAME);
        tagObject = nameSpace;
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
        updateResult();
    }

    /**
     * 
     */
    private void updateResult() {

        result += XML_STAT_TAG_CHAR + TO_MIM_MAP + CHAR_SPACE + TO_MIM_ATTRIB_NS + CHAR_EQUAL + CHAR_DOUBLE_QUOAT + nameSpace
                + CHAR_DOUBLE_QUOAT + CHAR_SPACE + TO_MIM_ATTRIB_NS_VER + CHAR_EQUAL + CHAR_DOUBLE_QUOAT + version.toString()
                + CHAR_DOUBLE_QUOAT + "\n";
    }
}
