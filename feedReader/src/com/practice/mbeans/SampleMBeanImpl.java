/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.practice.mbeans;

import javax.management.*;

public class SampleMBeanImpl implements SampleMBean {

    private String name = "Un-Defined";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getCounter() {
        return 10;
    }

    @Override
    public void printYourName() {
        System.out.println("My Name is :" + name);
    }

    @Override
    public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
        return "NONE";
    }

    @Override
    public void setAttribute(Attribute attribute)
            throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
        
    }

    @Override
    public AttributeList getAttributes(String[] attributes) {
        return null;
    }

    @Override
    public AttributeList setAttributes(AttributeList attributes) {
        return null;
    }

  
    @Override
    public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
        return null;
    }

    
    @Override
    public MBeanInfo getMBeanInfo() {
        MBeanInfo info = new MBeanInfo(this.getClass().getName(), "SampleCLass", null, null, null, null);
        return info;
    }

}
