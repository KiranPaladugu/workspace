/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.dot.jax.examples;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SimpleXmlElement {

    private String name;
    
    private String value;
    
    public String getName(){
        return this.name;
    }
    
    public String getValue(){
        return this.value;
    }
    
    public void setValue(String value){
        this.value = value;
    }
    
    public void setName(String name){
        this.name = name;
    }
}
