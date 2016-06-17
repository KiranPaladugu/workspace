/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.lyte.core.code;

import com.lyte.core.api.Container;
import com.lyte.core.api.Context;

public class Application implements com.lyte.core.api.Application {
    
    private static Context applicationContext= new com.lyte.core.code.Context();
    private static Container applicationContainer = new com.lyte.core.code.Container();
    
    public int startApplication(){
        initApplicationContext();
        initApplicationContainer();
        return 0;
    }

    /**
     * 
     */
    private void initApplicationContainer() {
    }

    /**
     * 
     */
    private void initApplicationContext() {
        
    }

    /* (non-Javadoc)
     * @see com.lyte.core.api.Application#getContext()
     */
    @Override
    public Context getContext() {
        return applicationContext;
    }

    /* (non-Javadoc)
     * @see com.lyte.core.api.Application#getContainer()
     */
    @Override
    public Container getContainer() {
        return applicationContainer;
    }

}
