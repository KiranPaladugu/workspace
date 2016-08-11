/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tools.plugin.xmltreeviewer.tech;

import java.io.*;

import javax.xml.parsers.*;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tools.plugin.xmltreeviewer.Watch;
import com.tools.plugin.xmltreeviewer.ui.ApplicationContext;

public class SimpleSaxXmlParser {
    private XmlNode node;
    public boolean parse(File file) {
        String data = getDataFromFile(file);
        return parse(data);
    }

    /**
     * @param data
     * @return 
     */
    public boolean parse(String data) {
        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            saxParser.getXMLReader().setFeature("http://xml.org/sax/features/validation", false);
            saxParser.getXMLReader().setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            saxParser.getXMLReader().setFeature("http://apache.org/xml/features/allow-java-encodings", false);
            SimpleSaxXmlHandler handler = new SimpleSaxXmlHandler();
            Watch watch = new Watch();
            watch.startWatch("XML Parsing");
//            StringBuilder builder = new StringBuilder();
//            builder.append("<data>");
//            builder.append(data);
//            builder.append("</data>");
//            data = "<data>"+data+"</data>";
//            data = data.replaceAll("\\P{Print}", "");
            saxParser.parse(new InputSource( new StringReader(data.replaceAll("\\P{Print}", ""))), handler);            
//            saxParser.parse(new InputSource( new StringReader(data)), handler);
            watch.stop();
            node = handler.getRootNode();
//            System.out.println(handler.getRootNode().toXml());
           
            watch.stats();
            return true;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            ApplicationContext.putErrorMessageInQueue(new Message(e, Message.EXCEPTION_MESSAGE));
        }
        return false;
    }
    
    public XmlNode getNodeModel(){
        return node;
    }

    public String getDataFromFile(File fileName) {
        StringBuffer data = new StringBuffer("");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = null;
            while ((line = reader.readLine()) != null) {
                data.append(line);
                data.append('\n');
            }
            reader.close();
        } catch (Exception e) {

        }
        return data.toString();
    }
}
