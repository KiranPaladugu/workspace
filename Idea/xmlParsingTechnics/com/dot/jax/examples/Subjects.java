/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.dot.jax.examples;

import java.util.List;

public class Subjects {

    private List<Subject> subject;

    public List<Subject> getSubject() {
        return subject;
    }

    public void setSubject(List<Subject> subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Subjects [subject=" + subject + "]";
    }
    
}
