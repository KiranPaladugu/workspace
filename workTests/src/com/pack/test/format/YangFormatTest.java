/* ********************************************************************************
 * All rights reserved to Kiran Paladugu. If you find any thing useful send your
 * valueble feeback to paladugukiran@gmail.com.
 ******************************************************************************* */
package com.pack.test.format;

import java.io.File;

public class YangFormatTest {
    public static void main(String args[]) {
        Fixer fixer = new Fixer();
        fixer.addFixerComponent(new FormatFixer("max-elements", ";"));
        fixer.addFixerComponent(new FormatFixer("min-elements", ";"));
        fixer.addFixerComponent(new FormatFixer("deviate", "}", 2));
        File file = new File("C:\\Users\\ekirpal\\Desktop\\YangModelsFromNode(All)\\YANG\\input");
        String fixedFilefolder = "C:\\Users\\ekirpal\\Desktop\\YangModelsFromNode(All)\\YANG\\input\\fixed\\";
        File[] files = file.listFiles();
        for (File yangFile : files) {
            if (!yangFile.isDirectory()) {
                System.out.println("Executing format fixer on the file :" + yangFile.getName());
                String fixed = fixer.exec(FileReaderUtil.readFile(yangFile.getAbsolutePath()));
                FileWriterUtil.writeToFile(fixed, fixedFilefolder + yangFile.getName());

            }
        }
    }
}
