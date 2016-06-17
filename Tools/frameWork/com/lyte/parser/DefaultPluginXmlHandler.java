package com.lyte.parser;

import java.io.IOException;
import java.util.Stack;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

public class DefaultPluginXmlHandler extends DefaultHandler {

    private TagHandlerProvider provider;
    private Stack<Object> stack;
    private Object current;
    private TagHandler currentTagHanlder;

    /**
     * 
     */
    public DefaultPluginXmlHandler(TagHandlerProvider provider) {
        this.provider = provider;
        stack = new Stack<Object>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#startDocument()
     */
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String,
     * org.xml.sax.Attributes)
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(current !=null){
            stack.push(current);
        }
        currentTagHanlder = provider.getTagHandler(localName);
        if(currentTagHanlder!=null){
            currentTagHanlder.startElement(uri, localName, qName, attributes);
        }
//        super.startElement(uri, localName, qName, attributes);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if(currentTagHanlder!=null){
            currentTagHanlder.characters(ch, start, length);
        }
        super.characters(ch, start, length);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(currentTagHanlder!=null){
            currentTagHanlder.endElement(uri, localName, qName);
        }
//        super.endElement(uri, localName, qName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#endDocument()
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#warning(org.xml.sax.SAXParseException)
     */
    @Override
    public void warning(SAXParseException e) throws SAXException {
        super.warning(e);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#error(org.xml.sax.SAXParseException)
     */
    @Override
    public void error(SAXParseException e) throws SAXException {
        super.error(e);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#fatalError(org.xml.sax.SAXParseException)
     */
    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        super.fatalError(e);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#skippedEntity(java.lang.String)
     */
    @Override
    public void skippedEntity(String name) throws SAXException {
        // TODO Auto-generated method stub
        super.skippedEntity(name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#unparsedEntityDecl(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) throws SAXException {
        // TODO Auto-generated method stub
        super.unparsedEntityDecl(name, publicId, systemId, notationName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#startPrefixMapping(java.lang.String, java.lang.String)
     */
    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        // TODO Auto-generated method stub
        super.startPrefixMapping(prefix, uri);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#endPrefixMapping(java.lang.String)
     */
    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        // TODO Auto-generated method stub
        super.endPrefixMapping(prefix);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#ignorableWhitespace(char[], int, int)
     */
    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        // TODO Auto-generated method stub
        super.ignorableWhitespace(ch, start, length);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#notationDecl(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void notationDecl(String name, String publicId, String systemId) throws SAXException {
        // TODO Auto-generated method stub
        super.notationDecl(name, publicId, systemId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#processingInstruction(java.lang.String, java.lang.String)
     */
    @Override
    public void processingInstruction(String target, String data) throws SAXException {
        // TODO Auto-generated method stub
        super.processingInstruction(target, data);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#resolveEntity(java.lang.String, java.lang.String)
     */
    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws IOException, SAXException {
        // TODO Auto-generated method stub
        return super.resolveEntity(publicId, systemId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#setDocumentLocator(org.xml.sax.Locator)
     */
    @Override
    public void setDocumentLocator(Locator locator) {
        // TODO Auto-generated method stub
        super.setDocumentLocator(locator);
    }

}
