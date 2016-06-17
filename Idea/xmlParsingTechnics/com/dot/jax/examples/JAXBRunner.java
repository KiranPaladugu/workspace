/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.dot.jax.examples;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXB;

public class JAXBRunner {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Address address = new Address();
        Subject subject = new Subject();
        subject.setName("English");
        subject.setId("12");
        Subject subject2 = new Subject();
        subject2.setName("Maths");
        subject2.setId("11");
        address.setHouse("Some house number");
        address.setStreet("Some stree name");
        address.setCity("SomeCity");
        address.setState("anyState");
        address.setZipcode(95667);
        Student student = new Student();
        student.setName("MyStudent");
        student.setId(1001);
        student.setStandard(8);
        student.setSection("A");
        student.setAge(15);
        student.setAddress(address);
        List<Subject> subs = new ArrayList<>();
        
        subs.add(subject);
        subs.add(subject2);
        Subjects subjects = new Subjects();
        subjects.setSubject(subs);
        student.setSubject(subs);
        
        JAXB.marshal(student, new File("C:\\Users\\ekirpal\\JAXBTest.xml"));
        
        Student stdnt=JAXB.unmarshal(new File("C:\\Users\\ekirpal\\JAXBTest.xml"), Student.class);
        System.out.println(stdnt);

    }

}
