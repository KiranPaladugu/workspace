/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tools.plugin.xmltreeviewer.tech;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public abstract class AbstractSimpleTagHandler extends DefaultHandler{
    
    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//        log("%s class will be handling the tag <%s> with Object #%s",this.getClass().getSimpleName(),qName,this.hashCode());
    }
    
    public abstract Object getTransformedObject();
    public void log(String message) {
        System.out.println(message);
    }

    public void log(String format, Object... objects) {
        System.out.println(String.format(format, objects));
    }
}
