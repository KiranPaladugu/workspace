/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.pack.mp2mim;

import java.io.*;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.*;

import org.xml.sax.*;

import com.pack.mp2mim.handlers.MpXMLDefaultHander;

public class MpToMimGenerator {
    /**
     * 
     */
    public static final String TO_MIM_MAP = "mimMappedTo";
    public static final String TO_MIM_ATTRIB_NS ="namespace";
    public static final String TO_MIM_ATTRIB_NS_VER="version";
    public static final String TO_MIM_ATTRIB_REF_NS="referenceMimNamespace";
    public static final String TO_MIM_ATTRIB_REF_NS_VER="referenceMimVersion";
    
    public static final String FROM_TAG_MIM= "mim";
    public static final String FROM_TAG_IMPLEMENTS="implements";    
    public static final String FROM_ATTRIBUTE_NAME="name";
    public static final String FROM_ATTRIBUTE_VERSION="version";
    public static final String FROM_ATTRIBUTE_REVISION="revision";
    public static final String FROM_ATTRIBUTE_RELEASE="release";
    public static final String FROM_ATTRIBUTE_CORRECTION="correction";
    
    public static final String XML_STAT_TAG_CHAR ="<";
    public static final String XML_END_TAG_CHAR=">";
    public static final String XML_SINGLE_LINE_END_CHAR="/";
    public static final String CHAR_SPACE=" ";
    public static final String CAHR_TAB ="\t";
    public static final String CHAR_EQUAL="=";
    public static final String CHAR_DOUBLE_QUOAT="\"";
    public static MIMGeneratorStats stats  = new MIMGeneratorStats();
    private File file;
    public MpToMimGenerator(String inputPath) {
        file = new File(inputPath);
        if(!file.exists()){
            file =null;
        }
    }
    
    public String prepareSingleLineTag(String tagName,Map<String,String> attributes){
        StringBuilder data = new StringBuilder("");
            data.append(XML_STAT_TAG_CHAR);
            data.append(tagName);
             Set<String> attributeList = attributes.keySet();
             for(String attrib:attributeList){
                 String value = attributes.get(attrib);                 
                 data.append(CHAR_SPACE);
                 data.append(attrib);
                 data.append(CHAR_EQUAL);
                 data.append(CHAR_DOUBLE_QUOAT);
                 data.append(value);
                 data.append(value);
             }
            data.append(CHAR_SPACE);
            data.append(XML_SINGLE_LINE_END_CHAR);
            data.append(XML_END_TAG_CHAR);
            
        return data.toString();
    }
    
    public String generateMimInfo(){
        generatteMinFrom(file);
       System.out.println("\n\n"+stats);
       return null;
    }
    
    private String generatteMinFrom(File file){
        stats.fileCount++;
        if(file.exists()&&file.isDirectory()){
            stats.folderCount++;
            File[] filesList = file.listFiles();
            for(File eachFile:filesList){
                 generatteMinFrom(eachFile);
            }
        }else if(file.exists()&&file.getName().endsWith(".xml")){
             generateMimInfo(file);
            stats.readFileCount++;
        }else{
            stats.skippedFileCount++;
        }
        return null;
    }

    public String generateMimInfo(File file){
        if(!file.exists() || file.isDirectory()){
            return null;
        }
            try {
//                System.out.println("Generating from :"+file.getName());
                SAXParser parser = SAXParserFactory.newInstance().newSAXParser();  
                parser.getXMLReader().setFeature("http://xml.org/sax/features/validation", false);
                parser.getXMLReader().setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
               /* parser.getXMLReader().setEntityResolver(new EntityResolver()
                {
                    public InputSource resolveEntity(String publicId, String systemId)
                            throws SAXException, IOException {
                        if (systemId.contains("mp.dtd")) {
                            return new InputSource(new StringReader(""));
                        } else {
                            return null;
                        }
                    }
                });*/
                MpXMLDefaultHander dh = new MpXMLDefaultHander();
                parser.parse(file, dh);
                String mim = dh.getResult();
                System.out.println();
                System.out.println(mim);
                if(mim!=null && mim.length()>0){
                    stats.generatedMims++;
                }
            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            }
        return null;
    }
    
    public static void main(String args[]){
        MpToMimGenerator gen = new MpToMimGenerator("C:\\ER6000\\NEW_NRM\\NRM");
        gen.generateMimInfo();
    }
}
