/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2012
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RunmeToTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String fullString = "I will come and meet you at the 123woods";
        String partWord = "123woods";
        String pattern = ".*" + partWord + ".*";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(fullString);
        System.out.println(m.matches());
    }

}
