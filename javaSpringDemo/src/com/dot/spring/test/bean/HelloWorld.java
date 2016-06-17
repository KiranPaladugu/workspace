/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.dot.spring.test.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.DisposableBean;

public class HelloWorld implements DisposableBean {    
    private String message="HelloWorld!";
    private List<String> messageList = new ArrayList<String>();

    /**
     * 
     */
    public HelloWorld(String value) {
       if(value==null){
           throw new RuntimeException("Invalid Value");
       }
       log("Constructing Object with value "+value);
    }
    
    public HelloWorld(){
        log("Default Constructor called.");
    }
    
    public void init(){
        log("Init called.");
    }
    
    public void setMessage(String message){
        this.messageList.add(message);
        this.message = message;
    }
    
    public String getMessage(){
        log("Your Message is %s",  message);
        log("List of Messages: %s",messageList);
        log("Current object:%s",this);
        return this.message;
    }
    
    public void destroy(){
        log("Destroy called on :"+this);
    }
    
    private void log(String format, Object... objects) {
        format = this.getClass().getSimpleName()+" - "+format;
        System.out.println(String.format(format, objects));
    }
}
