/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tools;

public class Request<T> extends Message<T>{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Request() {
        setMessageType(Message.REQUEST);
        this.setReplyExpected(true);
    }
    
    public Request(T message){
        this();
        this.setMessage(message);
    }
    
    
    
}
