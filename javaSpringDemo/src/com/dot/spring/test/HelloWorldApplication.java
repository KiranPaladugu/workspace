/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.dot.spring.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dot.spring.test.bean.*;

public class HelloWorldApplication {
    public static HelloWorld world;

    public static void main(String[] args) {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        context.registerShutdownHook();
        HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
        obj.setMessage("New Message");
        obj.getMessage();
        ((HelloWorld) context.getBean("helloWorld")).getMessage();
        String value = ((Testbean) context.getBean("BeanNameTest")).getBeanName();
        log("Value is :" + value);
        LifeCycleControllableBean lifeBean = (LifeCycleControllableBean) context.getBean("lifeTest");
        log("Message from lifeBean: %s" , lifeBean.getMessage());
        AbstractApplicationContext annotatedContext = new AnnotationConfigApplicationContext(AnnotationExample.class);        
        HelloWorld newWorld = (HelloWorld) annotatedContext.getBean("AnnotatedHello");
        log("Message from newWorld:",newWorld.getMessage());
        context.close();
        annotatedContext.close();
    }
    
    private static void log(String format, Object... objects) {
        System.out.println(String.format(HelloWorldApplication.class.getSimpleName() + " - " + format, objects));
    }
}
