/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.test;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;



/**
 * SAX Parser to read required attributes of FDN from Netconf filtered response.
 */
public class ReadNonPersistenceAttributeParser extends DefaultHandler {

    private static final String EMPTY_VALUE = "";
    private final StringBuilder characters = new StringBuilder();
    private boolean read = false;
    private boolean moTypeFound = false;
    private boolean moAttributeFound = false;
    private boolean primaryKeyAttributeFound = false;
    private boolean moWithIdFound = false;
    private boolean moAttributeIsComplexType = false;
    private boolean moAttributeIsSequence = false;
    private List<String> attributes = new ArrayList<>();
    private final Set<String> attributeAlreadyFound = new HashSet<String>();
    private String fdnType;
    private List<String> primaryKeyAttributes = new ArrayList<>();
    private String keyValue;
    private String key = null;

    private Map<String, Object> attributeMap;
    
    Map<String, Object> complexMapAttribute = new HashMap<>();

    public void setAttributes(final List<String> attributes) {
        this.attributes = attributes;
    }

    public void setFdnType(final String fdnType) {
        this.fdnType = fdnType;
    }

    public void setPrimaryKeyAttributes(final List<String> primaryKeyAttributes) {
        this.primaryKeyAttributes = primaryKeyAttributes;
    }

    public void setKeyValue(final String keyValue) {
        this.keyValue = keyValue;
    }

    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attribute) throws SAXException {
        if (qName.equalsIgnoreCase(fdnType)) {
            if (!moWithIdFound) {
                moTypeFound = true;
            } else {
                moWithIdFound = false;
            }
        } else if (primaryKeyAttributes.contains(qName) && moTypeFound) {
            primaryKeyAttributeFound = true;
            read = true;
        } else if (attributes.contains(qName) && moWithIdFound) {
            if (attributeAlreadyFound.contains(qName)) {
                moAttributeIsSequence = true;
            }
            attributeAlreadyFound.add(qName);
            key = qName;
            moAttributeFound = true;
            read();
            read = true;
            moAttributeIsComplexType=false;

        }else if(moTypeFound && moWithIdFound && moAttributeFound){
            moAttributeIsComplexType = true;
            read();
            read = true;
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public void endElement(final String uri, final String localName, final String qName) throws SAXException {
        final String text;
        if (primaryKeyAttributes.contains(qName) || attributes.contains(qName)) {
            if (moTypeFound && moWithIdFound && moAttributeFound) {
                text = readBuffer();
                if (key != null) {
                    if(moAttributeIsSequence) {
                        if(moAttributeIsComplexType){
                            if(null != attributeMap.get(key) && attributeMap.get(key)!= EMPTY_VALUE){
                                final List<Map<String,Object>> listOfComplexAttribute = new ArrayList<>();
                                if(attributeMap.get(key) instanceof List){
                                    listOfComplexAttribute.addAll((List<Map<String, Object>>) attributeMap.get(key));
                                }else{
                                    listOfComplexAttribute.add((Map<String, Object>) attributeMap.get(key));
                                }
                                listOfComplexAttribute.add(new HashMap<String, Object>(complexMapAttribute));
                                attributeMap.put(key, new ArrayList<Map<String, Object>>(listOfComplexAttribute));
                           
                            }else{
                                attributeMap.put(key, new HashMap<String, Object>(complexMapAttribute));
                            }
                            complexMapAttribute.clear();
                        }else{
                            final List<Object> attributeValueList = new ArrayList<>();
                            if(null != attributeMap.get(key) && attributeMap.get(key)!= EMPTY_VALUE){
                                if(attributeMap.get(key) instanceof List){
                                    attributeValueList.addAll((List<Object>) attributeMap.get(key));
                                }else{
                                    attributeValueList.add(attributeMap.get(key));
                                }
                                attributeValueList.add(text);
                                attributeMap.put(key, new ArrayList<Object>(attributeValueList));
                            }else{
                                attributeMap.put(key,text);      
                            }
                        }
                    
                    } else {
                        if (moAttributeIsComplexType) {
                            attributeMap.put(key,new HashMap<String, Object>(complexMapAttribute));
                            complexMapAttribute.clear();
                        } else {
                            attributeMap.put(key,text);      
                        }
                    }
                    key = null;
                    moAttributeFound =false;
                }
            } else {
                if (primaryKeyAttributeFound) {
                    if (readBuffer().equalsIgnoreCase(keyValue)) {
                        moWithIdFound = true;
                    } else {
                        primaryKeyAttributeFound = false;
                        moWithIdFound = false;
                        moTypeFound = false;
                        key = null;
                    }
                }
            }
        }else if (moTypeFound && moWithIdFound && moAttributeFound && moAttributeIsComplexType){
            text = readBuffer();
            complexMapAttribute.put(qName, text);
            
        }
    }

    @Override
    public void characters(final char[] ch, final int start, final int length) throws SAXException {
        if (read) {
            characters.append(new String(ch, start, length));
        }
    }

    /**
     * This method will parse the netconf filter response.
     * 
     * @param data
     *            : netconf response xml
     */
    public void parseData(final String data) {
        attributeMap = new HashMap<>();
        //Initialize attribute value map for attribute
       /* for (String attributeName : attributes) {
            attributeMap.put(attributeName, EMPTY_VALUE);
        }*/

        final SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        try {
            final SAXParser parser = parserFactor.newSAXParser();
            parser.parse(new InputSource(new StringReader(data)), this);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to get parsed attribute-attributeValue map
     * 
     * @return {@link Map} attribute map
     */
    public Map<String, Object> getAttributeMap() {
        return attributeMap;

    }

    private void read() {
        read = true;
        characters.setLength(0);
    }

    private String readBuffer() {
        final String chars = characters.toString();
        characters.setLength(0);
        read = false;
        return chars;
    }
    
    public static void main(String[] args) {
        String data = "<ManagedElement xmlns=\"urn:com:ericsson:ecim:ComTop\">            <managedElementId>1</managedElementId>            <SystemFunctions>                <systemFunctionsId>1</systemFunctionsId>                <SwM xmlns=\"urn:com:ericsson:ecim:IprSwM\">                    <swMId>1</swMId>                    <UpgradePackage>                        <state>PREPARE_COMPLETED</state>                        <upgradePackageId>10</upgradePackageId>                        <administrativeData struct=\"ProductData\">                            <productName>Router6672_R1A01_0001_release_upgrade_package</productName>                            <productNumber>CXP9027695_1</productNumber>                            <productRevision>30Dec02:16:532015evzzcdm</productRevision>                            <productionDate>2015-12-30T02:19:22</productionDate>                            <description>Router 6672</description>                            <type>Release</type>                        </administrativeData>                        <activationStep struct=\"ActivationStep\">                            <serialNumber>1</serialNumber>                            <name>Activation(one-go)</name>                            <description>Activation in one-go</description>                        </activationStep>                        <created>1970-01-01T01:55:16</created>                        <creatorActionId>11</creatorActionId>                    </UpgradePackage>                </SwM>            </SystemFunctions>        </ManagedElement>";
        ReadNonPersistenceAttributeParser parser = new ReadNonPersistenceAttributeParser();
        parser.setFdnType("UpgradePackage");
        
        parser.parseData(data);
        parser.getAttributeMap();
        System.out.println(parser.getAttributeMap());

    }
    
}