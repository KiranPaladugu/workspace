/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.lyte.parser;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class TagHandlerProvider {
    private String fileName = "PluginXMLParser.properties";
    private Properties properties = new Properties();

    /**
     * 
     */
    public TagHandlerProvider() {
        try {
            properties.load(this.getClass().getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Properties getProperties(){
        return this.properties;
    }
    
    public String getTagHandlerName(String name){
        return properties.getProperty(name);
    }

    @SuppressWarnings("unchecked")
    public Class<TagHandler> getTagHandlerClass(String name) {
        String value = properties.getProperty(name);
        if (value != null) {
            try {
                return (Class<TagHandler>) Class.forName(value);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public TagHandler getTagHandler(String name) {
        Class<TagHandler> tagHandlerClass = getTagHandlerClass(fileName);
        if (tagHandlerClass != null) {
            try {
                return tagHandlerClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String args[]) {
        long stratTime = System.currentTimeMillis();
        TagHandlerProvider provider =new TagHandlerProvider();
        Set<Entry<Object, Object>> keys = provider.getProperties().entrySet();
        Iterator<Entry<Object, Object>> itr = keys.iterator();
        System.out.println("Time taken to load : "+(System.currentTimeMillis()-stratTime));
        while(itr.hasNext()){
            Entry<Object, Object> entry = itr.next();
            System.out.println("ENTRY: "+entry.getKey()+" = "+entry.getValue());
        }
        System.out.println("Time taken in millis : "+(System.currentTimeMillis()-stratTime));
    }
}
