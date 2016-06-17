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
package com.bccl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class URLReader {
    /**
     * 
     */
    public URLReader() {
    }
    
    public String readURL(String url){
        StringBuilder builder = new StringBuilder();
        try {
            URL urlObj = new URL(url);
            BufferedInputStream streamBuf = new BufferedInputStream(urlObj.openStream());
            int read = -1;
            while((read=streamBuf.read())!=-1){
                builder.append((char)read);
            }
            streamBuf.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }   
    
    public static void main(String args[]){
     URLReader reader = new URLReader();
     String page = reader.readURL("http://sfbay.craigslist.org/search/cto");
     Document document = Jsoup.parse(page);
     Elements elements = document.getElementsByTag("a");
     Iterator<Element> elementItr = elements.iterator();
     while(elementItr.hasNext()){
         Element element = elementItr.next();
         System.out.println(element.html());
     }
    }
}
