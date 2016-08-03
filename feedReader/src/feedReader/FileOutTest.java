/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package feedReader;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileOutTest {
    /**
     * 
     */
    public void printASCIFileName(File rootDir){
        File[] files = rootDir.listFiles();
        for(File file:files){
            if(file.isDirectory()){
                printASCIFileName(file);
                if(!file.exists()){
                    if(!file.getParentFile().exists()){
                        file.getParentFile().mkdirs();
                    }
                }
            }
            System.out.println(file.toURI().toASCIIString());
        }
    }
    
    public FileOutTest() {
        final Path modelStore = Paths.get("C:\\Users\\ekirpal\\Documents\\CLI\\models");
        String frgName = "ComFm.xml";
        Path newPath = modelStore.resolve(frgName);
        System.out.println(newPath.toString());
        File rootDir = new File("C:\\Users\\ekirpal\\Documents\\CLI\\swSync\\output");
       printASCIFileName(rootDir);
/*
        try {
            FileOutputStream fileout = new FileOutputStream(
                    "C:\\Users\\ekirpal\\AppData\\Local\\Temp\\swsync-8157188035315820056\\OutputSPFRER60011\\etc\\model\\dps_primarytype\\urn%3ardns%3acom%3aericsson%3aoammodel%3aericsson-bgp-ipos\\ipv4%24%24unicast%24%24advertise-external\\ipv4%24%24unicast%24%24advertise-external-2016.3.21.xml");
            fileout.write("This is Sample Text To write..".getBytes());
            fileout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
    }

    public static void main(String args[]) {
        new FileOutTest();
    }
}
