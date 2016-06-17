/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.dot.spring.test.bean;

import org.springframework.beans.factory.annotation.Qualifier;

@Qualifier(value="beanName")
public class BeanAnnotationTest implements Testbean {
    private String beanName;

    /**
     * @return the beanName
     */
    public String getBeanName() {
        log("getBeanName()");
        return beanName;
    }

    /**
     * @param beanName the beanName to set
     */
    public void setBeanName(String beanName) {
        log("setBeanName()");
        this.beanName = beanName;
    }
    
    private void log(String format, Object... objects) {
        System.out.println(String.format(this.getClass().getSimpleName() + " - " + format, objects));
    }
}
