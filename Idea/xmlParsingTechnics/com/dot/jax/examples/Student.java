/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.dot.jax.examples;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlType(propOrder={"name","age","id","standard","section","address","subject"})
public class Student {
    private String name;
    private int id;
    private int standard;
    private String section;
    private Address address;
    private int age;
    private List<Subject> subject = new ArrayList<>();
    public synchronized int getAge() {
        return age;
    }
    @XmlElement(defaultValue="10")
    public synchronized void setAge(int age) {
        this.age = age;
    }
    
    public synchronized List<Subject> getSubject() {
        return subject;
    }
    
    public void addSubject(Subject subject){
        if(this.subject!=null){
            this.subject.add(subject);
        }
    }
    @XmlElementWrapper(name="subjects-list")
    public synchronized void setSubject(List<Subject> subjects) {
        this.subject = subjects;
    }
    public synchronized String getName() {
        return name;
    }
    public synchronized void setName(String name) {
        this.name = name;
    }
    public synchronized int getId() {
        return id;
    }
    public synchronized void setId(int id) {
        this.id = id;
    }
    public synchronized int getStandard() {
        return standard;
    }
    @XmlElement(required=true)
    public synchronized void setStandard(int standard) {
        this.standard = standard;
    }
    public synchronized String getSection() {
        return section;
    }
    public synchronized void setSection(String section) {
        this.section = section;
    }
    public synchronized Address getAddress() {
        return address;
    }
    public synchronized void setAddress(Address address) {
        this.address = address;
    }
    @Override
    public String toString() {
        return "Student [name=" + name + ", id=" + id + ", standard=" + standard + ", section=" + section + ", address=" + address
                + "]";
    }
    
    
    
}
