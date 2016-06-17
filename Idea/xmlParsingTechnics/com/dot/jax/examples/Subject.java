/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.dot.jax.examples;

public class Subject {
    private String name;
    private String id;
    public synchronized String getName() {
        return name;
    }
    public synchronized void setName(String name) {
        this.name = name;
    }
    public synchronized String getId() {
        return id;
    }
    public synchronized void setId(String id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "Subject [name=" + name + ", id=" + id + "]";
    }
    
    
}
