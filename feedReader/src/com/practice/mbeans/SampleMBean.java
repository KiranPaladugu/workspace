/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.practice.mbeans;

import javax.management.DynamicMBean;

public interface SampleMBean extends DynamicMBean{
    public String getName();
    public void setName(String name);
    public int getCounter();
    public void printYourName();
}
