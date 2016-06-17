/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.lyte.core.code;

import java.util.HashMap;
import java.util.Map;

public class Context implements com.lyte.core.api.Context {
    private Map<String, Object> contextMap = new HashMap<String,Object>();

    /* (non-Javadoc)
     * @see com.lyte.core.api.Context#get(java.lang.String)
     */
    @Override
    public Object get(String key) {
        return contextMap.get(key);
    }

    /* (non-Javadoc)
     * @see com.lyte.core.api.Context#put(java.lang.String, java.lang.Object)
     */
    @Override
    public void put(String key, Object value) {
        contextMap.put(key, value);
    }
    
    
}
