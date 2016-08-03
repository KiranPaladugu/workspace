/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package feedReader;

import java.io.*;

public class TestParser {
    public void doTest() {
        String fileName = "C:\\Users\\ekirpal\\Desktop\\yang.yang";
        String data = getDataFromFile(new File(fileName));
        if (data != null) {
            QueryResponseReaderAdapter adaptor = new QueryResponseReaderAdapter();
            String value;
            try {
                value = adaptor.parse(data);
                System.out.println(value);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public String getDataFromFile(File file) {
        if (file.exists() && file.isFile() && file.canRead()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuffer buffer = new StringBuffer();
                String data = null;
                while ((data = reader.readLine()) != null) {
                    buffer.append(data + "\n");
                }
                reader.close();
                return buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public static void main(String[] args){
        new TestParser().doTest();
    }
}
