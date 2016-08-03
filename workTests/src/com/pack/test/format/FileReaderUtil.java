/* ********************************************************************************
 * All rights reserved to Kiran Paladugu. If you find any thing useful send your
 * valueble feeback to paladugukiran@gmail.com.
 ******************************************************************************* */
package com.pack.test.format;

import java.io.*;

public class FileReaderUtil {
    public static String readFile(String path) {
        StringBuffer _toReturn = new StringBuffer();
        File _file = new File(path);
        if (_file.exists() && _file.isFile() && _file.canRead()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(_file));
                String _tmpstr = null;
                while ((_tmpstr = br.readLine()) != null) {
                    _toReturn.append(_tmpstr + "\n");
                }
                br.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (_toReturn.length() == 0)
            return null;
        return _toReturn.toString();
    }

}
