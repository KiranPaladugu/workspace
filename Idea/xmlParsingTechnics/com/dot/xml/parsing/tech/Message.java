/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.dot.xml.parsing.tech;

public class Message {
    public static int STRING_MESSAGE=1;
    public static int EXCEPTION_MESSAGE=3;    
    private Object payLoad;
    private int messageType;
    
    /**
     * 
     */
    public Message(Object payLoad,int type) {
        this.payLoad = payLoad;
        this.messageType = type;
    }
    
    /**
     * @return the payLoad
     */
    public Object getPayLoad() {
        return payLoad;
    }
    /**
     * @param payLoad the payLoad to set
     */
    public void setPayLoad(Object payLoad) {
        this.payLoad = payLoad;
    }
    /**
     * @return the messageType
     */
    public int getMessageType() {
        return messageType;
    }
    /**
     * @param messageType the messageType to set
     */
    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
}
