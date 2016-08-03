/* ********************************************************************************
 * All rights reserved to Kiran Paladugu. If you find any thing useful send your
 * valueble feeback to paladugukiran@gmail.com.
 ******************************************************************************* */
package com.pack.test.format;

import java.io.*;

public class FileWriterUtil {
    public static boolean writeToFile(String data,String filePath){
        File _file = new File(filePath);
        if(!_file.getParentFile().exists()){
            _file.getParentFile().mkdirs();
        }
        try {
            BufferedWriter _writer = new BufferedWriter(new FileWriter(_file));
            _writer.write(data);
            _writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
