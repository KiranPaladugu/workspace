/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tools.plugin.xmltreeviewer.ui;

import java.util.HashMap;
import java.util.Map;

import com.tools.plugin.xmltreeviewer.tech.BlockingQueue;
import com.tools.plugin.xmltreeviewer.tech.Message;

public class ApplicationContext {
    private static Map<Object,Object> contextMap  = new HashMap<Object,Object>();
    private static Map<String,Object> keyMap = new HashMap<String,Object>();
    private static BlockingQueue<Message> errorMessage = new BlockingQueue<>(1);
    
    public static synchronized Object getObject(String key){
        return keyMap.get(key);
    }
    
    public static synchronized Object putObject(String key,Object object){
        return keyMap.put(key, object);
    }
    
    public static synchronized void putErrorMessageInQueue(Message message){
        try {
            errorMessage.insert(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static synchronized Message getErrorMessageFromQueue(){
        try {
            return  errorMessage.remove(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
