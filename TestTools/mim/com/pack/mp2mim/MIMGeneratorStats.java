/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.pack.mp2mim;

public class MIMGeneratorStats {
    int fileCount;
    int skippedFileCount;
    int readFileCount;
    int generatedMims;
    int folderCount;
    @Override
    public String toString() {
        return "MIMGeneratorStats [Totalfiles=" + fileCount + ", skippedFileCount=" + skippedFileCount + ", readFileCount="
                + readFileCount + ", generatedMims=" + generatedMims + ", folderCount=" + folderCount + "]";
    }
   
    
    

}
