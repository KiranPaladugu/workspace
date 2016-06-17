/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.pack.mp2mim.resources;

import java.io.InputStream;
import java.net.URL;

public class ResourceLocator {
    public static URL getResource(String name){
       return ResourceLocator.class.getResource(name);
    }
    
    public static InputStream getResourceAsStream(String name){
        return ResourceLocator.class.getResourceAsStream(name);
    }
}
