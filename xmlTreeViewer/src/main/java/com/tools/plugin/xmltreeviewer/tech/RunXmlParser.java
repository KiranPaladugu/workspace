/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tools.plugin.xmltreeviewer.tech;

import java.io.File;

public class RunXmlParser {

    /**
     * @param args
     */
    public static void main(String[] args) {
//        String fileName ="C:\\Users\\ekirpal\\Documents\\Altova\\Router6000Equipment_mp.xml";
//        String fileName ="C:\\GIT\\er6000-cm-vertical-slice\\testsuite\\integration\\jee\\target\\deployed-model\\2016-04-04_12-01-17\\dps_primarytype\\ComSysM\\NetconfTls\\NetconfTls-3.1.0.xml";
        String fileName="C:\\Users\\ekirpal\\Desktop\\getResponse.xml";        
        SimpleSaxXmlParser parser = new SimpleSaxXmlParser();
        parser.parse(new File(fileName));
    }

}
