/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.lyte.core.code;

import java.util.List;
import java.util.Vector;

public class Container implements com.lyte.core.api.Container {

    public List<Object> list = new Vector<Object>();

    /*
     * (non-Javadoc)
     * 
     * @see com.lyte.core.api.Container#add(java.lang.Object)
     */
    @Override
    public void add(Object object) {
        list.add(object);
    }
    
    public boolean contains(Object object){
        return list.contains(object);
    }
    
    public List<Object> getAll(){
        return list;
    }

}
