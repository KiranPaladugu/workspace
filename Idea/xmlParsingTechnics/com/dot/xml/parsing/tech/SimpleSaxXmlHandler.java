/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.dot.xml.parsing.tech;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.pack.dot.UnlimitedStack;

public class SimpleSaxXmlHandler extends DefaultHandler{
    private UnlimitedStack<XmlNode> stack = new UnlimitedStack<XmlNode>();
    private TagHandlerHelper helper = new TagHandlerHelper();
    private XmlNode rootNode;
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//        log("StartElemnt "+qName);
        String q_name=qName;
        if(qName.contains(":")){
           q_name=q_name.replace(':','_');
        }
        AbstractSimpleTagHandler simpleHanlder = helper.getTagHanlder(q_name);
        if(simpleHanlder!=null){
            simpleHanlder.startElement(uri, localName, qName, attributes);
        }
        XmlNode node = new XmlNode(qName,uri);
        XmlNode parent = stack.peek();
        if(parent!=null){
            parent.addChild(node);
        }
        
        for(int i=0;i<attributes.getLength();i++){
            node.addAttribute(attributes.getQName(i), attributes.getValue(i));
        }
        stack.push(node);
    }
    
   
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        XmlNode node = stack.peek();
        node.append(ch, start, length);
    }
    
   
    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        super.ignorableWhitespace(ch, start, length);
        System.out.println(new String(ch,start,length));
    }
    
    
 
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
//        log("EndElement :"+qName);
        XmlNode currentNode = stack.peek();
        if(currentNode.isAttribute()){
            XmlNode parentNode = currentNode.getParent();
            parentNode.removeChild(currentNode);
            parentNode.addAttribute(currentNode.getTagName(), currentNode.getValue());
        }
        rootNode = stack.pop();
    }
    
    public XmlNode getRootNode(){
        return this.rootNode;
    }
    
}
