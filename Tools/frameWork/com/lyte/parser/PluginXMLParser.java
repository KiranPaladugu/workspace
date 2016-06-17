package com.lyte.parser;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class PluginXMLParser extends AbstractParser {

    private String fileName;

    @Override
    public Object startParsing() {
        return null;
    }

    @Override
    public synchronized Object parse() {
        synchronized (fileName) {
            Object object = null;
            File file = new File(fileName);
            if (file.exists() && file.canRead()) {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                try {
                    SAXParser parser = factory.newSAXParser();
                    TagHandlerProvider provider = new TagHandlerProvider();
                    parser.parse(file, new DefaultPluginXmlHandler(provider));
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return object;
        }
    }

    public PluginXMLParser(String fileName) {
        this.fileName = fileName;
    }

}
