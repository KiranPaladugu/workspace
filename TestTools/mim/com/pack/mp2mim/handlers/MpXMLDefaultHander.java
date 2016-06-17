/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.pack.mp2mim.handlers;

import static com.pack.mp2mim.MpToMimGenerator.*;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.pack.mp2mim.Version;

public class MpXMLDefaultHander extends DefaultTagHandler {
    private TagHandlerUtils utils = new TagHandlerUtils();
    private DefaultTagHandler currentHandler;
    private DefaultTagHandler mimHandler;
    private boolean updated;
    private Version version = new Version();
    private boolean procced = true;
    private String ns_vs = "";
    private String ref_ns;

    /**
     * 
     */
    public MpXMLDefaultHander() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String,
     * org.xml.sax.Attributes)
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        DefaultTagHandler handler = utils.getTagHandlerObject(qName);
        if (qName.equals("derivedDataType")) {
            procced = false;
        } else if (qName.equals("implements")) {
            procced = true;
        }
        if (handler != null && procced) {
            currentHandler = handler;
            if (qName.equals("mim")) {
                handler.startElement(uri, localName, qName, attributes);
                ns_vs = currentHandler.result;
                mimHandler = currentHandler;
            } else if (qName.equals("domainExtension")) {
                //                ref_ns = currentHandler.getResult();
            } else if (qName.equals("extension")) {
                handler.setVersion(version);
                handler.startElement(uri, localName, qName, attributes);
                if (ref_ns == null || ref_ns.length() < 1) {
                    ref_ns = handler.tagObject;
                }
            } else {
                handler.startElement(uri, localName, qName, attributes);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("domainExtension") || qName.equals("derivedDataType")) {
            procced = false;

        } else if (qName.equals("implements") && !updated) {
            procced = false;
            updated = true;
            ns_vs += currentHandler.getResult();
            setResult(ns_vs);
        } else if (qName.equals("models")) {
            if (!updated && ref_ns != null) {
                String ref_data = TO_MIM_ATTRIB_REF_NS + CHAR_EQUAL + CHAR_DOUBLE_QUOAT + ref_ns + CHAR_DOUBLE_QUOAT + CHAR_SPACE;
                ns_vs += ref_data + TO_MIM_ATTRIB_REF_NS_VER + CHAR_EQUAL + CHAR_DOUBLE_QUOAT + version.toString()
                        + CHAR_DOUBLE_QUOAT + CHAR_SPACE + XML_SINGLE_LINE_END_CHAR + XML_END_TAG_CHAR;
                updated = true;
                setResult(ns_vs);
            }
            if (!updated) {
                ns_vs += TO_MIM_ATTRIB_REF_NS + CHAR_EQUAL + CHAR_DOUBLE_QUOAT + mimHandler.getTagObject() + CHAR_DOUBLE_QUOAT
                        + CHAR_SPACE + TO_MIM_ATTRIB_REF_NS_VER + CHAR_EQUAL + CHAR_DOUBLE_QUOAT
                        + (version.isEmpty() ? mimHandler.getVersionAsString() : version.toString()) + CHAR_DOUBLE_QUOAT
                        + CHAR_SPACE + XML_SINGLE_LINE_END_CHAR + XML_END_TAG_CHAR;
                ;
                setResult(ns_vs);
            }
        }
    }

}
