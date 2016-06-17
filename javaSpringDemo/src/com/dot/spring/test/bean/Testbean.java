/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.dot.spring.test.bean;

import javax.management.MXBean;

@MXBean
public interface Testbean {
    public String getBeanName();
    public void setBeanName(String beanName);
}
