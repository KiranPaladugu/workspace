/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.dot.spring.test.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnnotationExample {
    @Bean(name="AnnotatedHello" ,initMethod="init")
    public HelloWorld getHelloWorld(){
        return new HelloWorld("From Annotation");
    }
}
