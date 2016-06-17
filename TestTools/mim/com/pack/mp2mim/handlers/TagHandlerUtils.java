/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.pack.mp2mim.handlers;

import java.io.IOException;
import java.util.Properties;

import com.pack.mp2mim.resources.ResourceLocator;

public class TagHandlerUtils {
    private Properties properties = new Properties();
    private String propertyFileName = "TagHandler.properties";
   
    /**
     * 
     */
    public TagHandlerUtils() {
        try {
            properties.load(ResourceLocator.getResourceAsStream(propertyFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
   @SuppressWarnings("unchecked")
public Class<DefaultTagHandler> getTagHandeler(String tagName){
       String handlerClass = (String) properties.get(tagName);
       if(handlerClass!=null && handlerClass.length()>0){
           try {
           return (Class<DefaultTagHandler>) Class.forName(handlerClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
       }
       return null;
   }
   
   public DefaultTagHandler getTagHandlerObject(String tagName){
       Class<DefaultTagHandler> calssName = getTagHandeler(tagName);
       if(calssName!=null){
           try {
            return calssName.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
       }
       return null;
   }
}
