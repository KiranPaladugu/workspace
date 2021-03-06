/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tool.ssh;

import java.net.URL;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;
 
public class URLConnectionEx
{
  public static void main(String[] args)
  throws Exception
  {
    String httpsURL = "https://cifwk-oss.lmera.ericsson.se/ENM/16.3/mediaContent/ERICenm_CXP9027091/1.18.90/";
    URL myurl = new URL(httpsURL);
    
    HttpsURLConnection con = (HttpsURLConnection)myurl.openConnection();
    InputStream ins = con.getInputStream();
    InputStreamReader isr = new InputStreamReader(ins);
    BufferedReader in = new BufferedReader(isr);
 
    String inputLine;
 
    while ((inputLine = in.readLine()) != null)
    {
      System.out.println(inputLine);
    }
 
    in.close();
  }
}