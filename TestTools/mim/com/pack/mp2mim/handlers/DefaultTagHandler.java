/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.pack.mp2mim.handlers;

import org.xml.sax.helpers.DefaultHandler;

import com.pack.mp2mim.Version;

public class DefaultTagHandler extends DefaultHandler{
    protected String result="";
    protected String tagObject;
    protected Version version = new Version();
    
    public Version getVersion() {
        return version;
    }
    public void setVersion(Version version) {
        this.version = version;
    }
    public String getResult(){
        return this.result;
    }
    protected void setResult(String result){
        this.result = result;
    }
    public String getVersionAsString(){
        return version.toString();
    }
    public String getTagObject() {
        return tagObject;
    }
    public void setTagObject(String tagObject) {
        this.tagObject = tagObject;
    }

}
