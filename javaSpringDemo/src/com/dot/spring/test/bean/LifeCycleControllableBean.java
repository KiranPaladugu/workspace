/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.dot.spring.test.bean;

public class LifeCycleControllableBean {
    private String message;

    public void setMessage(String message) {
        log(" setMessage() called with argument %s", message);
        this.message = message;
    }

    public String getMessage() {
        log(" getMessage() called.");
        return this.message;
    }

    public void init() {
        log("init() called.");
        this.message = "Initalized";
    }

    public void destroy() {
        log(" destroy is called.");
    }

    private void log(String format, Object... objects) {
        System.out.println(String.format(this.getClass().getSimpleName() + " - " + format, objects));
    }
}
