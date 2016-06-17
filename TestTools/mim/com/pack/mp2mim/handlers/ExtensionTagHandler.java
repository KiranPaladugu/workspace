/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.pack.mp2mim.handlers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ExtensionTagHandler extends DefaultTagHandler {
    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String,
     * org.xml.sax.Attributes)
     */
    private String _version = "Version";
    private String _correction = "Correction";
    private String _release = "Release";
    private String _dName = "Name";
    private String _name = "name";
    private String _value = "value";

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (attributes.getValue(_name).endsWith(_dName)) {
            tagObject = attributes.getValue(_value);
        } else if (attributes.getValue(_name).endsWith(_version)) {
            version.setVersion(attributes.getValue(_value));
        } else if (attributes.getValue(_name).endsWith(_release)) {
            version.setRelease(attributes.getValue(_value));
        } else if (attributes.getValue(_name).endsWith(_correction)) {
            version.setCorrection(attributes.getValue(_value));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
    }

}
